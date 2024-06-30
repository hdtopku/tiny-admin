<script setup lang="ts">
import {Rule} from "ant-design-vue/es/form";
import {saveOrUpdate} from "@/api/user.ts";
import {message} from "ant-design-vue";

const formRules: Record<string, Rule[]> = {
  username: [
    {
      required: true,
      message: '请输入用户名',
      trigger: ['blur', 'change']
    },
    {
      type:'string',
      min: 3,
      max: 20,
      message: '用户名长度在 3 到 20 个字符',
    },
    {
      validator: (rule, value) => {
        return new Promise((resolve, reject) => {
          /^(?![0-9_])(?!.*?_$)[a-zA-Z0-9_]+$/.test(value)
              ? resolve()
              : reject('用户名必须唯一，长度在 3 到 20 个字符，只能包含字母、数字、下划线，且不能以数字、下划线开头，不能以下划线结尾')
        })
      }
    },
  ],
  nickname: [
    {
      required: true,
      message: '请输入昵称',
      trigger: ['blur', 'change']
    },
    {
      type:'string',
      min: 3,
      max: 20,
      message: '昵称长度在 3 到 20 个字符',
    }
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
          message.success('修改成功')
          emit('queryList')
        })
      })
      .catch(error => {
        console.log('error', error);
      });
}
const emit=defineEmits(['queryList'])
const curUserInfo = ref()
const username=ref('')

const roleList = ref()
const filteredOptions = computed(() => {
  return roleList.value.filter((item: any) => !curUserInfo.value.roleNames.includes(item.roleName)).map(item => ({
    value: item.roleName,
    label: item.roleName
  }))
})

defineExpose({
  showModal({roles, isEdit = false, userInfo}) {
    console.log(roles)
    console.log(userInfo)
        userInfoModalVisible.value = true
        isUpdate.value = isEdit
        curUserInfo.value = userInfo
        username.value = userInfo.username
    roleList.value = roles
      }
    }
)
</script>

<template>
  <a-modal v-model:open="userInfoModalVisible" destroy-on-close ok-text="提交" cancel-text="取消"
           :title="isUpdate? `编辑用户_${username}` : '新增用户'"
           @ok="handleSubmit">
    <a-form  class="pt-4"  @keydown.enter="handleSubmit" ref="formRef" :rules="formRules" :model="curUserInfo"
            :label-col="{ span: 4 }" autocomplete="off">
      <a-form-item label="用户名" name="username" help="用户名必须唯一，长度在 3 到 20 个字符，只能包含字母、数字、下划线，且不能以数字、下划线开头，不能以下划线结尾">
        <a-input v-model:value="curUserInfo.username" allow-clear autocomplete="off"/>
      </a-form-item>
      <a-form-item label="昵称" name="nickname">
        <a-input v-model:value="curUserInfo.nickname" allow-clear autocomplete="off"/>
      </a-form-item>
      <a-form-item label="邮箱">
        <a-input v-model:value="curUserInfo.email" allow-clear autocomplete="off"/>
      </a-form-item>
      <a-form-item label="手机号">
        <a-input v-model:value="curUserInfo.phone" allow-clear autocomplete="off"/>
      </a-form-item>
      <a-form-item label="角色"
      >
        <a-select key="id" v-model:value="curUserInfo.roleNames" :options="filteredOptions" allow-clear
                  mode="multiple" placeholder="请选择角色">
        </a-select>
      </a-form-item>
    </a-form>
  </a-modal>
</template>
