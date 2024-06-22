<script setup lang="ts">

import {Rule} from "ant-design-vue/es/form";
import {updatePassword} from "@/api/user.ts";
import {message} from "ant-design-vue";
const passwordModalVisible = ref(false)
const passwordItem = ref({
  id: '',
  password: '',
})
const username=ref('')
const passwordRules: Record<string, Rule[]> = reactive({
  password: [
    {required: true, message: '请输入新密码', trigger: ['blur'], whitespace: false},
    {min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: ['change', 'blur']},]
})
const passwordFormRef=ref()
const handleChangePassword = () => {
  passwordFormRef.value.validate().then(() => {
    updatePassword({...passwordItem.value}).then(()=>{
      passwordModalVisible.value = false
      message.success('密码更新成功')
    })
  })
}
defineExpose({
  showModal: (item: any) => {
    passwordModalVisible.value = true
    passwordItem.value = {id:item.key, password: item.password}
    username.value=item.username
  }
})
</script>

<template>
  <a-modal destroy-on-close ok-text="提交" cancel-text="取消" @ok="handleChangePassword" @cancel="()=>passwordItem = {}"
           v-model:open="passwordModalVisible" :title="`修改密码_${username}`">
    <a-form @keydown.enter="handleChangePassword" :model="passwordItem" ref="passwordFormRef" :rules="passwordRules">
      <a-form-item name="password" required>
        <a-input allow-clear placeholder="请输入新密码" v-model:value="passwordItem.password" autocomplete="off"></a-input>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<style scoped>

</style>