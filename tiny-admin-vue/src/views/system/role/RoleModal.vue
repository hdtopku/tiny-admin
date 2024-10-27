<template>
  <a-modal
      :cancel-text="$t('取消')"
      :ok-text="$t('提交')"
      v-model:open="modalVisible"
      :title="isUpdate ? $t('编辑角色-{curName}', { curName: curName }) : $t('新增角色')
    "
      @ok="handleSubmit"
  >
    <template #footer>
      <a-button type="default" @click="modalVisible = false">取消</a-button>
      <a-button :loading="loading" type="primary" @click="handleSubmit">提交</a-button>
    </template>
    <a-form
        ref="formRef"
        :label-col="{ span: 4 }"
        :model="curInfo"
        :rules="rules"
        class="pt-4"
        @keydown.enter.prevent="handleSubmit"
    >
      <a-form-item :label="$t('角色名称')" name="roleName">
        <a-input
            v-model:value="curInfo.roleName"
            :placeholder="$t('请输入角色名称')"
            allow-clear
            autocomplete="off"
        />
      </a-form-item>
      <a-form-item :label="$t('角色描述')" name="roleDesc">
        <a-input
            v-model:value="curInfo.description"
            :placeholder="$t('请输入角色描述')"
            allow-clear
            autocomplete="off"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script lang="ts" setup>
import {t} from '@/utils/i18n.ts'

import {Rule} from 'ant-design-vue/es/form'
import {saveOrUpdateRole} from '@/api/system/role.ts'
import {message} from 'ant-design-vue'

const formRef = ref(), loading = ref(false), curInfo = ref({id: null, roleName: '', description: ''})
const emit = defineEmits(['queryList']), modalVisible = ref(false)
let isUpdate = false, curName = ''

const handleSubmit = () => {
  formRef.value.validate().then(() => {
    loading.value = true
    saveOrUpdateRole({
      id: curInfo.value.id,
      roleName: curInfo.value.roleName,
      description: curInfo.value.description,
    }).then(() => {
      modalVisible.value = false
      emit('queryList')
      message.success(t('角色已更新'))
    }).finally(() => {
      loading.value = false
    })
  })
}

defineExpose({
  openModal(roleInfo: any = {}) {
    modalVisible.value = true
    isUpdate = !!roleInfo.id
    curName = roleInfo.roleName
    curInfo.value = Object.assign({}, roleInfo)
  },
})
const rules: Record<string, Rule[]> = {
  roleName: [
    {required: true, message: t('请输入角色名称'), trigger: 'blur'},
    {
      min: 2,
      max: 20,
      message: t('长度在 2 到 20 个字符'),
      trigger: ['blur', 'change'],
    },
  ],
}
</script>
