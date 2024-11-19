<template>
  <a-modal
      v-model:open="open"
      :title="isUpdate ? 'Edit product' : 'Add product'"
      destroy-on-close
      @ok="handleOk"
      width="800px"
  >
    <template #footer>
      <a-space class="flex justify-end">
        <a-button key="back" @click="open = false">Cancel</a-button>
        <a-button
            key="submit"
            :loading="formLoading"
            type="primary"
            @click="handleOk"
        >Submit
        </a-button
        >
      </a-space>
    </template>
    <a-tabs v-model:activeKey="activeKey">
      <a-tab-pane key="basicInfo" tab="Basic Info"></a-tab-pane>
      <a-tab-pane
          key="detailInfo"
          tab="product Detail"
          force-render
      ></a-tab-pane>
    </a-tabs>
    <div style="height: 400px; overflow: auto">
      <!-- Basic Info -->
      <a-form
          v-show="activeKey === 'basicInfo'"
          ref="formRef"
          :label-col="{ span: 4 }"
          :model="productInfo"
          :rules="rules"
      >
        <a-form-item label="Album" name="logo">
          <ImageUpload
              v-model:image-urls="productInfo.album"
              :count="10"
           />
        </a-form-item>
        <a-form-item label="Name" name="productName">
          <a-textarea
              v-model:value="productInfo.productName"
              placeholder="Please enter the name of the product"
              allowClear
              :rows="2"
          />
        </a-form-item>
        <a-form-item label="Description" name="productDesc">
          <a-textarea
              v-model:value="productInfo.productDesc"
              placeholder="Please enter the description of the product"
              :rows="3"
              allowClear
          />
        </a-form-item>
        <div class="flex justify-around">
          <a-form-item
              label="Sale Price"
              :label-col="{ span: 14 }"
              name="promotionPrice"
          >
            <a-input-number
                v-model:value="productInfo.salePrice"
                :max="99999999"
                :min="0"
                :precision="2"
            />
          </a-form-item>
          <a-form-item
              label="Market Price"
              :label-col="{ span: 14 }"
              name="marketPrice"
          >
            <a-input-number
                v-model:value="productInfo.marketPrice"
                :max="99999999"
                :min="0"
                :precision="2"
            />
          </a-form-item>
        </div>
        <div class="flex justify-around">
          <a-form-item
              label="Stock"
              :label-col="{ span: 14 }"
              name="stock"
          >
            <a-input-number
                v-model:value="productInfo.stock"
                :max="99999999"
                :min="0"
                default-value="9999"
            />
          </a-form-item>
          <a-form-item
              label="Stock Alert"
              :label-col="{ span: 14 }"
              name="lowStock"
          >
            <a-input-number
                v-model:value="productInfo.lowStock"
                :max="99999999"
                :min="0"
                default-value="0"
            />
          </a-form-item>
        </div>
      </a-form>
      <!-- Product Detail -->
      <RichTextEditor
          v-show="activeKey === 'detailInfo'"
          v-model:model-value="productInfo.detailHtml"
      ></RichTextEditor>
    </div>
  </a-modal>
</template>
<script lang="ts" setup>

import {Ref, ref} from 'vue'
import {message} from 'ant-design-vue'
import ImageUpload from '@/components/ImageUpload.vue'
import RichTextEditor from '@/components/RichTextEditor.vue'

const {
  saveOrUpdate = () => {
  }, queryList = () => {
  }
} = defineProps({
  saveOrUpdate: Function,
  queryList: Function,
})

const open = ref<boolean>(false), isUpdate = ref<boolean>(false), productInfo = ref<any>({})
const formRef = ref(), formLoading = ref(false), activeKey = ref('basicInfo'), emit = defineEmits(['queryList'])

const handleOk = () => {
  formRef.value
      .validate()
      .then(() => {
        formLoading.value = true
        saveOrUpdate(productInfo.value)
            .then(() => {
              message.success('Save Success')
              open.value = false
              queryList()
            })
            .finally(() => {
              formLoading.value = false
            })
      })
      .catch((err) => {
        console.log('error', err)
      })
}
const defaultProductInfo = {
  id: '',
  productName: '',
  productDesc: '',
  album: [],
  detailHtml: '',
  stock: 9999,
  lowStock: 10,
}
defineExpose({
  openModal: (product: any = {}) => {
    isUpdate.value = !!product.id
    productInfo.value = {...defaultProductInfo, ...product}
    open.value = true
  },
})

const rules: Ref<any> = ref({
  productName: [
    {
      required: true,
      message: 'Please enter the name of the product',
      trigger: ['blur', 'change'],
    },
    {
      min: 1,
      max: 50,
      message: 'Length should be between 1 and 50 characters',
      trigger: ['blur', 'change'],
    },
  ],

  productDesc: [
    {
      required: true,
      message: 'Please enter the description of the product',
      trigger: ['blur', 'change'],
    },
    {
      min: 1,
      max: 200,
      message: 'Length should be between 1 and 200 characters',
      trigger: ['blur', 'change'],
    },
  ],

  promotionPrice: [
    {required: true, message: 'Please enter the sale price', trigger: ['blur', 'change']},
    {type: 'number', message: 'Please enter a number', trigger: ['blur', 'change']},
  ],

  stock: [
    {required: true, message: 'Please enter the stock', trigger: ['blur', 'change']},
    {type: 'number', message: 'Please enter a number', trigger: ['blur', 'change']},
  ],
})
</script>
<style lang="less" scoped>
:deep(.ant-upload-picture-card-wrapper) {
  width: auto;
}
</style>