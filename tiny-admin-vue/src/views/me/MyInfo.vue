<script setup lang="ts">
import {useUserStore} from "@/store";
import {LoadingOutlined, PlusOutlined} from '@ant-design/icons-vue';
import type {UploadChangeParam, UploadProps} from 'ant-design-vue';
import {message} from 'ant-design-vue';
import {updateSelfInfo} from "@/api/auth.ts";
import useGlobal from "@/hooks/useGlobal.ts";

const open = ref<boolean>(false);

const {$bus} = useGlobal()
$bus.on('show-my-info-modal', () => {
  open.value = true;
})
const handleOk = () => {
  updateSelfInfo(personalInfo).then(() => {
    message.success('保存成功')
    $bus.emit('update-user-info')
  })
}

const fileList = ref([]);
const loading = ref<boolean>(false);

const handleChange = (info: UploadChangeParam) => {
  if (info.file.status === 'uploading') {
    loading.value = true;
    return;
  }
  if (info.file.status === 'done') {
    // Get this url from response in real world.
    personalInfo.avatar = info.file.response.data;
    loading.value = false;
  }
  if (info.file.status === 'error') {
    loading.value = false;
    message.error('upload error');
  }
};

const beforeUpload = (file: UploadProps['fileList'][number]) => {
  const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
  if (!isJpgOrPng) {
    message.error('You can only upload JPG file!');
  }
  const isLt2M = file.size / 1024 / 1024 < 2;
  if (!isLt2M) {
    message.error('Image must smaller than 2MB!');
  }
  return isJpgOrPng && isLt2M;
}
const {menuTree, publicMenuList, ...personalInfo} = useUserStore().userInfo
</script>

<template>
  <a-modal v-model:open="open" ok-text="提交" cancel-text="取消" title="个人信息" @ok="handleOk">
    <div class="text-center">
      <a-upload
          v-model:file-list="fileList"
          list-type="picture-card"
          class="mx-auto"
          :show-upload-list="false"
          action="http://localhost:3000/api/file/upload"
          :before-upload="beforeUpload"
          @change="handleChange"
      >
        <img v-if="personalInfo.avatar" :src="personalInfo.avatar" alt="avatar"/>
        <div v-else>
          <loading-outlined v-if="loading"></loading-outlined>
          <plus-outlined v-else></plus-outlined>
          <div class="ant-upload-text">Upload</div>
        </div>
      </a-upload>
    </div>
    <a-form :label-col="{style: { width: '60px' }}" :model="personalInfo">
      <a-form-item label="用户名" name="username">
        <a-input v-model:value="personalInfo.username" allow-clear></a-input>
      </a-form-item>
      <a-form-item label="昵称">
        <a-input v-model:value="personalInfo.nickname" allow-clear></a-input>
      </a-form-item>
      <a-form-item label="手机号">
        <a-input v-model:value="personalInfo.phone" allow-clear></a-input>
      </a-form-item>
      <a-form-item label="邮箱">
        <a-input v-model:value="personalInfo.email" allow-clear></a-input>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<style scoped>

</style>