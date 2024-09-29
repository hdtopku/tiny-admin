<script lang="ts" setup>
import {Ref, ref} from 'vue';
import {saveOrUpdateBrand} from "@/api/pms/brand.ts";
import {message} from "ant-design-vue";
import ImageUpload from "@/components/ImageUpload.vue";

const open = ref<boolean>(false);
const isUpdate = ref<boolean>(false);
const brandInfo = ref<any>({});
const rules: Ref<any> = ref({
  brandName: [
    {required: true, message: '请输入品牌名称', trigger: ['blur', 'change']},
    {min: 1, max: 50, message: '长度在 2 到 50 个字符', trigger: ['blur', 'change']}
  ],
  brandDesc: [
    {required: true, message: '请输入品牌描述', trigger: ['blur', 'change']},
    {min: 1, max: 200, message: '长度在 1 到 100 个字符', trigger: ['blur', 'change']}
  ],
})
const formRef = ref()
const formLoading = ref(false)
const emits = defineEmits(['queryList']);
const handleOk = () => {
  formRef.value.validate().then(() => {
    formLoading.value = true;
    saveOrUpdateBrand(brandInfo.value).then(() => {
      message.success("操作成功");
      open.value = false
      emits('queryList')
    }).finally(() => {
      formLoading.value = false
    })
  }).catch((err) => {
    console.log('error', err)
  })
}

const showModal = (brand: any = {}) => {
  if (brand.id) {
    isUpdate.value = true;
    brandInfo.value = Object.assign({}, brand);
  } else {
    brandInfo.value = {}
  }
  open.value = true;
}

defineExpose({
  showModal
})

</script>
<template>
  <div>
    <a-modal destroy-on-close ok-text="提交" cancel-text="取消" v-model:open="open" :title="isUpdate? '更新品牌' : '新增品牌'"
             @ok="handleOk">
      <template #footer>
        <a-space class="flex justify-end">
          <a-button key="back" @click="open = false">取消</a-button>
          <a-button key="submit" :loading="formLoading" type="primary" @click="handleOk">提交</a-button>
        </a-space>
      </template>
      <a-form :label-col="{span: 4}" :model="brandInfo" :rules="rules" ref="formRef">
        <a-form-item label="品牌logo" name="logo">
          <div class="flex justify-center">
            <ImageUpload v-model:image-url="brandInfo.logo"></ImageUpload>
            <a-textarea :rows="2" v-model:value="brandInfo.logo" placeholder="或输入品牌logo链接" allowClear/>
          </div>
        </a-form-item>
        <a-form-item label="品牌名称" name="brandName">
          <a-input v-model:value="brandInfo.brandName" placeholder="请输入品牌名称" allowClear/>
        </a-form-item>
        <a-form-item label="品牌描述" name="brandDesc">
          <a-textarea :rows="2" v-model:value="brandInfo.brandDesc" placeholder="请输入品牌描述" allowClear/>
        </a-form-item>
        <a-form-item label="品牌故事" name="brandStory">
          <a-textarea :rows="5" v-model:value="brandInfo.brandStory" placeholder="请输入品牌描述" allowClear/>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

