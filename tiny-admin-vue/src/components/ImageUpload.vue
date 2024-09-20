<script setup lang="ts">

import {LoadingOutlined, PlusOutlined} from "@ant-design/icons-vue";
import {message, UploadChangeParam, type UploadProps} from "ant-design-vue";
import {ref} from "vue";

const loading = ref(false);
const fileList = ref<UploadProps['fileList']>([])
const props = defineProps({
  imageUrl: {
    type: String,
    default: '',
  },
  count: {
    type: Number,
    default: 0,
  },
  imageUrls: {
    type: Array<String>,
    default: [],
  }
})

let imageUrl=props.imageUrl
let imageUrls: Array<String> = props.imageUrls
imageUrls.forEach((url, idx) => {
  fileList.value.push({
    uid: idx,
    name: url.substring(url.lastIndexOf('/') + 1),
    status: 'done',
    url: url,
  })
})
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
    console.log(info.file, info.fileList)
    if (props.count < 1) {
      imageUrl = info.file.response.data;
      emits('update:imageUrl', imageUrl)
    } else {
      imageUrls = []
      info.fileList.map(file => {
        imageUrls.push(file.response.data)
      })
      emits('update:imageUrls', imageUrls)
    }
    loading.value = false;
  }
  if (info.file.status === 'error') {
    loading.value = false;
    message.error('upload error');
  }
};


const previewVisible = ref(false);
const previewImage = ref('');
const previewTitle = ref('');

function getBase64(file: File) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
  });
}

const handlePreview = async (file: UploadProps['fileList'][number]) => {
  if (!file.url && !file.preview) {
    file.preview = (await getBase64(file.originFileObj)) as string;
  }
  previewImage.value = file.url || file.preview;
  previewVisible.value = true;
  previewTitle.value = file.name || file.url.substring(file.url.lastIndexOf('/') + 1);
};
const handleCancel = () => {
  previewVisible.value = false;
  previewTitle.value = '';
};
</script>

<template>
  <!--  single upload -->
  <a-upload
      v-if="props.count < 1"
      v-model:file-list="fileList"
      list-type="picture-card"
      class="mx-auto"
      :show-upload-list="false"
      :multiple="false"
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
  <!--  multiple upload -->
  <div v-else>
    <a-upload
        v-model:file-list="fileList"
        :before-upload="beforeUpload"
        action="http://localhost:3000/api/file/upload"
        list-type="picture-card"
        @change="handleChange"
        @preview="handlePreview"
    >
      <div v-if="fileList.length < count">
        <plus-outlined/>
        <div style="margin-top: 8px">Upload</div>
      </div>
    </a-upload>
    <a-modal :footer="null" :open="previewVisible" :title="previewTitle" @cancel="handleCancel">
      <img :src="previewImage" alt="example" style="width: 100%"/>
    </a-modal>
  </div>
</template>

<style scoped>

</style>