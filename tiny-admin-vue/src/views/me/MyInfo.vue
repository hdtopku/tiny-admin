<script setup lang="ts">
import {useUserStore} from "@/store";

const open = ref<boolean>(true);

const showModal = () => {
  open.value = true;
};
const handleOk = () => {

}

import {LoadingOutlined, PlusOutlined} from '@ant-design/icons-vue';
import type {UploadChangeParam, UploadProps} from 'ant-design-vue';
import {message} from 'ant-design-vue';

function getBase64(img: Blob, callback: (base64Url: string) => void) {
  const reader = new FileReader();
  reader.addEventListener('load', () => callback(reader.result as string));
  reader.readAsDataURL(img);
}

const fileList = ref([]);
const loading = ref<boolean>(false);
const imageUrl = ref<string>('');

const handleChange = (info: UploadChangeParam) => {
  if (info.file.status === 'uploading') {
    loading.value = true;
    return;
  }
  if (info.file.status === 'done') {
    // Get this url from response in real world.
    getBase64(info.file.originFileObj, (base64Url: string) => {
      imageUrl.value = base64Url;
      loading.value = false;
    });
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
const personalInfo=useUserStore().userInfo
</script>

<template>
  <a-modal v-model:open="open" ok-text="提交" cancel-text="取消" title="个人信息" @ok="handleOk">
    <div class="text-center">
      <a-upload
          v-model:file-list="fileList"
          list-type="picture-card"
          class="mx-auto"
          :show-upload-list="false"
          action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
          :before-upload="beforeUpload"
          @change="handleChange"
      >
        <img v-if="imageUrl" :src="imageUrl" alt="avatar"/>
        <div v-else>
          <loading-outlined v-if="loading"></loading-outlined>
          <plus-outlined v-else></plus-outlined>
          <div class="ant-upload-text">Upload</div>
        </div>
      </a-upload>
    </div>
    <a-form :model="personalInfo">
      <a-form-item label="用户名" name="username">
        <a-input v-model:value="personalInfo.username"></a-input>
      </a-form-item>
      <a-form-item label="昵称">
        <a-input v-model:value="personalInfo.nickname"></a-input>
      </a-form-item>
      <a-form-item label="密码">
        <a-input></a-input>
      </a-form-item>
      <a-form-item label="手机号">
        <a-input v-model:value="personalInfo.phone"></a-input>
      </a-form-item>
      <a-form-item label="邮箱">
        <a-input v-model:value="personalInfo.email"></a-input>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<style scoped>

</style>