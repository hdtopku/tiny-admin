<script lang="ts" setup>
import {t} from '@/utils/i18n.ts'

import {Rule} from 'ant-design-vue/es/form'
import {updatePassword} from '@/api/system/user.ts'
import {message} from 'ant-design-vue'

const passwordModalVisible = ref(false)
const passwordItem = ref({
  id: '',
  password: '',
})
const username = ref('')
const passwordRules: Record<string, Rule[]> = reactive({
  password: [
    {
      required: true,
      message: t('请输入新密码'),
      trigger: ['blur'],
      whitespace: false,
    },
    {
      min: 6,
      max: 20,
      message: t('密码长度在 6 到 20 个字符'),
      trigger: ['change', 'blur'],
    },
  ],
})
const passwordFormRef = ref()
const handleChangePassword = () => {
  passwordFormRef.value.validate().then(() => {
    updatePassword({...passwordItem.value}).then(() => {
      passwordModalVisible.value = false
      message.success(t('密码更新成功'))
    })
  })
}
defineExpose({
  openModal: (item: any = {}) => {
    passwordModalVisible.value = true
    passwordItem.value = {id: item.key, password: item.password}
    username.value = item.username
  },
})
</script>

<template>
  <a-modal
      v-model:open="passwordModalVisible"
      :cancel-text="$t('取消')"
      :ok-text="$t('提交')"
      :title="$t('修改密码_{username}', { username: username })"
      @cancel="() => (passwordModalVisible = false)"
      @ok="handleChangePassword"
  >
    <a-form
        ref="passwordFormRef"
        :model="passwordItem"
        :rules="passwordRules"
        @keydown.enter="handleChangePassword"
    >
      <a-form-item name="password" required>
        <a-input
            v-model:value="passwordItem.password"
            :placeholder="$t('请输入新密码')"
            allow-clear
            autocomplete="off"
        ></a-input>
      </a-form-item>
    </a-form>
  </a-modal>
</template>
