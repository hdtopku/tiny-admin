<template>
  <a-modal
      v-model:open="userInfoModalVisible"
      :title="
      isUpdate
        ? 'Edit User ' + username
        : 'Add New User'
    "
      @ok="handleSubmit"
  >
    <template #footer>
      <a-button @click="userInfoModalVisible = false">Cancel</a-button>
      <a-button :loading="loading" type="primary" @click="handleSubmit">
        {{ isUpdate ? 'Submit' : 'Add' }}
      </a-button>
    </template>
    <a-form
        ref="formRef"
        :label-col="{ span: 4 }"
        :model="curUserInfo"
        :rules="formRules"
        autocomplete="off"
        class="pt-4"
        @keydown.enter="handleSubmit"
    >
      <a-form-item>
        <div class="text-center">
          <ImageUpload v-model:image-url="curUserInfo.avatar"/>
        </div>
      </a-form-item>
      <a-form-item
          :help="
            'Username must be unique, length between 2 and 20 characters, can only contain letters, numbers, and underscores, and cannot start with a number or underscore, or end with an underscore'
        "
          :label="'Username'"
          name="username"
      >
        <a-input
            v-model:value="curUserInfo.username"
            allow-clear
            autocomplete="off"
        />
      </a-form-item>
      <a-form-item :label="'Nickname'" name="nickname">
        <a-input
            v-model:value="curUserInfo.nickname"
            allow-clear
            autocomplete="off"
        />
      </a-form-item>
      <a-form-item :label="'Assign Role'">
        <a-select
            key="id"
            v-model:value="curUserInfo.roleNames"
            :options="filteredOptions"
            :placeholder="'Please select role'"
            allow-clear
            mode="multiple"
        >
        </a-select>
      </a-form-item>
      <a-form-item :label="'Email'">
        <a-input
            v-model:value="curUserInfo.email"
            allow-clear
            autocomplete="off"
        />
      </a-form-item>
      <a-form-item :label="'Phone Number'">
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
import {message} from 'ant-design-vue'

import {Rule} from 'ant-design-vue/es/form'
import {saveOrUpdate} from '@/api/system/user.ts'
import {getRoleList} from "@/api/system/role.ts"

const isUpdate = ref(false), userInfoModalVisible = ref(false), formRef = ref(), loading = ref(false)
const formRules: Record<string, Rule[]> = {
  username: [
    {
      required: true,
      message: 'Please enter a username',
      trigger: ['blur', 'change'],
    },
    {
      type: 'string',
      min: 2,
      max: 20,
      message: 'Username length must be between 2 and 20 characters',
    },
    {
      validator: (rule, value) => {
        return new Promise((resolve, reject) => {
          ;/^(?![0-9_])(?!.*?_$)[a-zA-Z0-9_]+$/.test(value)
              ? resolve()
              : reject(
                  'Username must be unique, length between 3 and 20 characters, can only contain letters, numbers, and underscores, and cannot start with a number or underscore, or end with an underscore'
              )
        })
      },
    },
  ],

  nickname: [
    {
      required: true,
      message: 'Please enter a nickname',
      trigger: ['blur', 'change'],
    },
    {
      type: 'string',
      min: 2,
      max: 20,
      message: 'Nickname length must be between 2 and 20 characters',
    },
  ],
}
const handleSubmit = () => {
  formRef.value
      .validate()
      .then(() => {
        loading.value = true
        saveOrUpdate(curUserInfo.value).then(() => {
          userInfoModalVisible.value = false
          message.success('Modification successful')
          emit('queryList')
        }).finally(() => {
          loading.value = false
        })
      })
      .catch((error) => {
        console.log('error', error)
      })
}
const emit = defineEmits(['queryList'])
const curUserInfo = ref()
const username = ref('')

const filteredOptions = computed(() => {
  return roleList.value?.filter(
      (item: any) =>
          !(curUserInfo.value.roleNames || []).includes(item.roleName)
  ).map((item) => ({
    value: item.roleName,
    label: item.roleName,
  }))
})

let roleList = ref()
defineExpose({
  openModal: (userInfo: any = {}) => {
    userInfoModalVisible.value = true
    if (!roleList.value?.length) {
      getRoleList().then((res: any) => {
        roleList.value = res
      })
    }
    isUpdate.value = !!userInfo?.id
    curUserInfo.value = {...userInfo}
    username.value = userInfo.username || ''
  }
})
</script>
