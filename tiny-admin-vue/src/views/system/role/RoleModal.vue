<script setup lang="ts">
import {Rule} from "ant-design-vue/es/form";
import {saveOrUpdate} from "@/api/role.ts";
import {message} from "ant-design-vue";

const rules: Record<string, Rule[]> = {
  roleName: [
    {required: true, message: '请输入角色名称', trigger: 'blur'},
    {min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: ['blur', 'change']},
  ],
}
const formRef = ref()
const curInfo = ref({
  id: null,
  roleName: '',
  description: '',
})
let emits = defineEmits(['queryList'])
const handleSubmit = () => {
  formRef.value.validate().then(() => {
    saveOrUpdate({
      id: curInfo.value.id,
      roleName: curInfo.value.roleName,
      description: curInfo.value.description
    }).then(() => {
      modalVisible.value = false
      emits('queryList')
      message.success('角色已更新')
    })
  })
}
const modalVisible = ref(false)

const handleCancel = () => {
  modalVisible.value = false
}
const isUpdate = ref(false)
const curName = ref('')
defineExpose({
  showModal(isEdit = false, roleInfo: any) {
    modalVisible.value = true
    isUpdate.value = isEdit
    curInfo.value = Object.assign({}, roleInfo)
    curName.value = roleInfo.roleName
  }
})
</script>

<template>
  <a-modal :title="isUpdate? `编辑角色-${curName}` : '新增角色'" :open="modalVisible" ok-text="提交" cancel-text="取消"
           @ok="handleSubmit" @cancel="handleCancel">
    <a-form @keydown.enter.prevent="handleSubmit" class="pt-4" :label-col="{ span: 4 }" ref="formRef" :rules="rules" :model="curInfo">
      <a-form-item name="roleName" label="角色名称">
        <a-input allow-clear v-model:value="curInfo.roleName" autocomplete="off" placeholder="请输入角色名称"/>
      </a-form-item>
      <a-form-item name="roleDesc" label="角色描述">
        <a-input allow-clear v-model:value="curInfo.description" autocomplete="off" placeholder="请输入角色描述"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<style scoped>

</style>