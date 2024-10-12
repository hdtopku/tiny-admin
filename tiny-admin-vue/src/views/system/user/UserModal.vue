<template>
  <a-modal
      v-model:open="userInfoModalVisible"
      :cancel-text="$t('取消')"
      :ok-text="$t('提交')"
      :title="
      isUpdate
        ? $t('编辑用户_{username}', { username: username })
        : $t('新增用户')
    "
      @ok="handleSubmit"
  >
    <a-form
        ref="formRef"
        :label-col="{ span: 4 }"
        :model="curUserInfo"
        :rules="formRules"
        autocomplete="off"
        class="pt-4"
        @keydown.enter="handleSubmit"
    >
      <a-form-item
          :help="
          $t(
            '用户名必须唯一，长度在 2 到 20 个字符，只能包含字母、数字、下划线，且不能以数字、下划线开头，不能以下划线结尾'
          )
        "
          :label="$t('用户名')"
          name="username"
      >
        <a-input
            v-model:value="curUserInfo.username"
            allow-clear
            autocomplete="off"
        />
      </a-form-item>
      <a-form-item :label="$t('用户昵称')" name="nickname">
        <a-input
            v-model:value="curUserInfo.nickname"
            allow-clear
            autocomplete="off"
        />
      </a-form-item>
      <a-form-item :label="$t('分配角色')">
        <a-select
            key="id"
            v-model:value="curUserInfo.roleNames"
            :options="filteredOptions"
            :placeholder="$t('请选择角色')"
            allow-clear
            mode="multiple"
        >
        </a-select>
      </a-form-item>
      <a-form-item :label="$t('邮箱')">
        <a-input
            v-model:value="curUserInfo.email"
            allow-clear
            autocomplete="off"
        />
      </a-form-item>
      <a-form-item :label="$t('手机号')">
        <a-input
            v-model:value="curUserInfo.phone"
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
import {saveOrUpdate} from '@/api/system/user.ts'
import {message} from 'ant-design-vue'

const formRules: Record<string, Rule[]> = {
  username: [
    {
      required: true,
      message: t('请输入用户名'),
      trigger: ['blur', 'change'],
    },
    {
      type: 'string',
      min: 2,
      max: 20,
      message: t('用户名长度在 2 到 20 个字符'),
    },
    {
      validator: (rule, value) => {
        return new Promise((resolve, reject) => {
          ;/^(?![0-9_])(?!.*?_$)[a-zA-Z0-9_]+$/.test(value)
              ? resolve()
              : reject(
                  t(
                      '用户名必须唯一，长度在 3 到 20 个字符，只能包含字母、数字、下划线，且不能以数字、下划线开头，不能以下划线结尾'
                  )
              )
        })
      },
    },
  ],

  nickname: [
    {
      required: true,
      message: t('请输入昵称'),
      trigger: ['blur', 'change'],
    },
    {
      type: 'string',
      min: 2,
      max: 20,
      message: t('昵称长度在 2 到 20 个字符'),
    },
  ],
}
const isUpdate = ref(false)
const userInfoModalVisible = ref(false)
const formRef = ref()
const handleSubmit = () => {
  formRef.value
      .validate()
      .then(() => {
        saveOrUpdate(curUserInfo.value).then(() => {
          userInfoModalVisible.value = false
          message.success(t('修改成功'))
          emit('queryList')
        })
      })
      .catch((error) => {
        console.log('error', error)
      })
}
const emit = defineEmits(['queryList'])
const curUserInfo = ref()
const username = ref('')

const roleList = ref()
const filteredOptions = computed(() => {
  return roleList.value
      .filter(
          (item: any) =>
              !(curUserInfo.value.roleNames || []).includes(item.roleName)
      )
      .map((item) => ({
        value: item.roleName,
        label: item.roleName,
      }))
})

defineExpose({
  showModal({roles, isEdit = false, userInfo}) {
    userInfoModalVisible.value = true
    isUpdate.value = isEdit ?? false
    curUserInfo.value = userInfo || {}
    username.value = userInfo.username || ''
    roleList.value = roles || []
  },
})
</script>
