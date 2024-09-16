<script setup lang="ts">

import {LoadingOutlined, PlusOutlined} from "@ant-design/icons-vue";
import {message, UploadChangeParam, type UploadProps} from "ant-design-vue";
const loading = ref(false);
const fileList = ref([]);
const props = defineProps({
  imageUrl: {
    type: String || [String],
    default: '',
  }
})

let imageUrl=props.imageUrl
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

const emits = defineEmits(['update:imageUrl'])
const handleChange = (info: UploadChangeParam) => {
  if (info.file.status === 'uploading') {
    loading.value = true;
    return;
  }
  if (info.file.status === 'done') {
    // Get this url from response in real world.
    imageUrl = info.file.response.data;
    loading.value = false;
    emits('update:imageUrl', imageUrl)
  }
  if (info.file.status === 'error') {
    loading.value = false;
    message.error('upload error');
  }
};
</script>

<template>
  <a-upload
      v-model:file-list="fileList"
      list-type="picture-card"
      class="mx-auto"
      :show-upload-list="false"
      action="http://localhost:3000/api/file/upload"
      :before-upload="beforeUpload"
      @change="handleChange"
  >
    <img v-if="props.imageUrl" :src="props.imageUrl" alt="avatar"/>
    <div v-else>
      <loading-outlined v-if="loading"></loading-outlined>
      <plus-outlined v-else></plus-outlined>
      <div class="ant-upload-text">Upload</div>
    </div>
  </a-upload>
</template>

<style scoped>

</style>