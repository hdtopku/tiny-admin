<template>
  <div>
    <a-modal
        v-model:open="open"
        cancel-text="Cancel"
        ok-text="Submit"
        :title="isUpdate ? 'Update Carousel Card' : 'Add Carousel Card'"
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
      <a-form
          v-show="activeKey === 'basicInfo'"
          ref="formRef"
          :label-col="{ span: 6 }"
          :model="bannerInfo"
          :rules="rules"
      >
        <a-form-item label="Carousel Image" name="logo">
          <div class="flex justify-center">
            <ImageUpload v-model:image-url="bannerInfo.picUrl"></ImageUpload>
          </div>
        </a-form-item>
        <a-form-item label="Card Name" name="bannerName">
          <a-input
              v-model:value="bannerInfo.bannerName"
              placeholder="Please enter the carousel card name"
              allowClear
          />
        </a-form-item>
        <a-form-item label="Redirect Link" name="linkTo">
          <a-textarea
              v-model:value="bannerInfo.linkTo"
              placeholder="Please enter the redirect link, supports http/https protocols"
              :rows="3"
              allowClear
          />
        </a-form-item>
        <div class="grid grid-cols-3 gap-4">
          <a-form-item
              label="Display Platform"
              :label-col="{ span: 8 }"
              name="platform"
              class="col-span-2"
          >
            <a-select v-model:value="bannerInfo.platform" :default-value="1">
              <a-select-option :value="1">PC</a-select-option>
              <a-select-option :value="2">Mobile</a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item
              label="Sort Order"
              :label-col="{ span: 8 }"
              name="sort"
          >
            <a-input-number
                v-model:value="bannerInfo.sort"
                :max="99999999"
                :min="0"
                default-value="9999"
            />
          </a-form-item>
        </div>
        <a-form-item label="Remarks" name="remark">
          <a-textarea
              v-model:value="bannerInfo.remark"
              placeholder="Remark information is only displayed in the backend management and will not be shown to users"
              :rows="3"
              allowClear
          />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>
<script lang="ts" setup>
import {Ref, ref} from 'vue'
import {message} from 'ant-design-vue'
import ImageUpload from '@/components/ImageUpload.vue'
const {saveOrUpdate, queryList} = defineProps({
  saveOrUpdate: Function,
  queryList: Function,
})

const open = ref<boolean>(false), isUpdate = ref<boolean>(false), bannerInfo = ref<any>({})
const formRef = ref(), formLoading = ref(false), emit = defineEmits(['queryList'])
const handleOk = () => {
  formRef.value.validate().then(() => {
    formLoading.value = true
    const {createTime, updateTime, loading, ...rest} = bannerInfo.value
    saveOrUpdate?.(rest)
        .then(() => {
          message.success('Save Success')
          open.value = false
          queryList?.()
        })
        .finally(() => {
          formLoading.value = false
        })
  }).catch((err) => {
    console.log('error', err)
  })
}
const activeKey = ref('basicInfo')

defineExpose({
  openModal: (banner: any = {}) => {
    if (banner.id) {
      isUpdate.value = true
      bannerInfo.value = Object.assign({}, banner)
    } else {
      bannerInfo.value = {}
    }
    open.value = true
  },
})

watch(bannerInfo, (newVal) => {
  console.log(newVal)
})

const rules: Ref<any> = ref({
  bannerName: [
    {
      required: true,
      message: 'Please enter the carousel card name',
      trigger: ['blur', 'change'],
    },
    {
      min: 1,
      max: 50,
      message: 'Length should be between 2 and 30 characters',
      trigger: ['blur', 'change'],
    },
  ],
})
</script>
