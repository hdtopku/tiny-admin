<template>
  <a-modal
      :cancel-text="'Cancel'"
      :ok-text="'Submit'"
      v-model:open="modalVisible"
      :title="isUpdate ? 'Edit Role ' + curName : 'Add New Role'"
      @ok="handleSubmit"
      width="800px"
  >
    <template #footer>
      <a-button type="default" @click="modalVisible = false">Cancel</a-button>
      <a-button :loading="loading" type="primary" @click="handleSubmit">Submit</a-button>
    </template>
    <a-form
        ref="formRef"
        :label-col="{ span: 6 }"
        :model="curInfo"
        :rules="rules"
        class="pt-4"
        @keydown.enter.prevent="handleSubmit"
    >
      <a-form-item :label="'Role Name'" name="roleName">
        <a-input
            v-model:value="curInfo.roleName"
            :placeholder="'Please enter role name'"
            allow-clear
            autocomplete="off"
        />
      </a-form-item>
      <a-form-item :label="'Role Description'" name="roleDesc">
        <a-textarea
            v-model:value="curInfo.description"
            :placeholder="'Please enter role description'"
            allow-clear
            autocomplete="off"
            :rows="3"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts" setup>
import { message } from 'ant-design-vue'
import { Rule } from 'ant-design-vue/es/form'

// Destructuring props
const { saveOrUpdate, queryList } = defineProps({
  saveOrUpdate: Function,
  queryList: Function
})

const formRef = ref()
const curInfo = ref<{ id: number | null, roleName: string, description: string }>({ id: null, roleName: '', description: '' })
const loading = ref(false)
const emit = defineEmits(['queryList'])
const modalVisible = ref(false)
let isUpdate = false
let curName = ''

// Handle form submission
const handleSubmit = async () => {
  try {
    await formRef.value.validate()
    loading.value = true
    await saveOrUpdate?.({
      id: curInfo.value.id,
      roleName: curInfo.value.roleName,
      description: curInfo.value.description
    })
    modalVisible.value = false
    queryList?.()
    message.success('Role has been updated')
  } catch (error) {
    console.error('Error while saving/updating:', error)
  } finally {
    loading.value = false
  }
}

// Expose method for opening modal
defineExpose({
  openModal(roleInfo: { id?: number, roleName: string, description: string }) {
    modalVisible.value = true;
    isUpdate = !!roleInfo.id;
    curName = roleInfo.roleName;
    curInfo.value = { ...roleInfo as { id: number | null; roleName: string; description: string; } };
  }
})

// Validation rules
const rules: Record<string, Rule[]> = {
  roleName: [
    { required: true, message: 'Please enter role name', trigger: 'blur' },
    {
      min: 2,
      max: 20,
      message: 'Length must be between 2 and 20 characters',
      trigger: ['blur', 'change']
    }
  ]
}
</script>
