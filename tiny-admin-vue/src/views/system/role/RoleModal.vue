<template>
  <a-modal
      :cancel-text="$t('取消')"
      :ok-text="$t('提交')"
      :open="modalVisible"
      :title="
      isUpdate ? $t('编辑角色-{curName}', { curName: curName }) : $t('新增角色')
    "
      @cancel="handleCancel"
      @ok="handleSubmit"
  >
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
import {saveOrUpdate} from '@/api/system/role.ts'
import {message} from 'ant-design-vue'

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
      description: curInfo.value.description,
    }).then(() => {
      modalVisible.value = false
      emits('queryList')
      message.success(t('角色已更新'))
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
  },
})
</script>
