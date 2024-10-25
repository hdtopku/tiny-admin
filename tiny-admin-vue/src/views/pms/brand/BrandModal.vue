<template>
  <div>
    <a-modal
        v-model:open="open"
        :cancel-text="$t('取消')"
        :ok-text="$t('提交')"
        :title="isUpdate ? $t('更新品牌') : $t('新增品牌')"
        destroy-on-close
        @ok="handleOk"
    >
      <template #footer>
        <a-space class="flex justify-end">
          <a-button key="back" @click="open = false">{{ $t('取消') }}</a-button>
          <a-button
              key="submit"
              :loading="formLoading"
              type="primary"
              @click="handleOk"
          >{{ $t('提交') }}
          </a-button
          >
        </a-space>
      </template>
      <a-form
          ref="formRef"
          :label-col="{ span: 4 }"
          :model="brandInfo"
          :rules="rules"
      >
        <a-form-item :label="$t('品牌logo')" name="logo">
          <ImageUpload v-model:image-url="brandInfo.logo"></ImageUpload>
          <a-form-item-rest>
            <a-textarea
                v-model:value="brandInfo.logo"
                :placeholder="$t('或输入品牌logo链接')"
                :rows="2"
                allowClear
            />
          </a-form-item-rest>
        </a-form-item>
        <a-form-item :label="$t('品牌名称')" name="brandName">
          <a-input
              v-model:value="brandInfo.brandName"
              :placeholder="$t('请输入品牌名称')"
              allowClear
          />
        </a-form-item>
        <a-form-item :label="$t('品牌描述')" name="brandDesc">
          <a-textarea
              v-model:value="brandInfo.brandDesc"
              :placeholder="$t('请输入品牌描述')"
              :rows="2"
              allowClear
          />
        </a-form-item>
        <a-form-item :label="$t('品牌故事')" name="brandStory">
          <a-textarea
              v-model:value="brandInfo.brandStory"
              :placeholder="$t('请输入品牌描述')"
              :rows="5"
              allowClear
          />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>
<script lang="ts" setup>
import {t} from '@/utils/i18n.ts'

import {Ref, ref} from 'vue'
import {saveOrUpdateBrand} from '@/api/pms/brand.ts'
import {message} from 'ant-design-vue'
import ImageUpload from '@/components/ImageUpload.vue'

const open = ref<boolean>(false)
const isUpdate = ref<boolean>(false)
const brandInfo = ref<any>({})
const rules: Ref<any> = ref({
  brandName: [
    {
      required: true,
      message: t('请输入品牌名称'),
      trigger: ['blur', 'change'],
    },
    {
      min: 1,
      max: 50,
      message: t('长度在 2 到 50 个字符'),
      trigger: ['blur', 'change'],
    },
  ],

  brandDesc: [
    {
      required: true,
      message: t('请输入品牌描述'),
      trigger: ['blur', 'change'],
    },
    {
      min: 1,
      max: 200,
      message: t('长度在 1 到 100 个字符'),
      trigger: ['blur', 'change'],
    },
  ],
})
const formRef = ref()
const formLoading = ref(false)
const emit = defineEmits(['queryList'])
const handleOk = () => {
  formRef.value
      .validate()
      .then(() => {
        formLoading.value = true
        saveOrUpdateBrand(brandInfo.value)
            .then(() => {
              message.success(t('操作成功'))
              emit('queryList', {})
              open.value = false
            })
            .finally(() => {
              formLoading.value = false
            })
      })
      .catch((err) => {
        console.log('error', err)
      })
}

const openModal = (brand: any = {}) => {
  if (brand.id) {
    isUpdate.value = true
    brandInfo.value = Object.assign({}, brand)
  } else {
    brandInfo.value = {}
  }
  open.value = true
}

defineExpose({
  openModal,
})
</script>
