<template>
  <a-modal
      v-model:open="open"
      cancel-text="Cancel"
      ok-text="Submit"
      :title="isUpdate ? 'Update Brand' : 'Add Brand'"
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
        >Submit</a-button>
      </a-space>
    </template>

    <a-form
        ref="formRef"
        :label-col="{ span: 6 }"
        :model="brandInfo"
        :rules="rules"
    >
      <a-form-item label="Brand Logo" name="logo">
        <div class="text-center">
          <ImageUpload v-model:image-url="brandInfo.logo"></ImageUpload>
        </div>
      </a-form-item>

      <a-form-item label="Brand Name" name="brandName">
        <a-input
            v-model:value="brandInfo.brandName"
            placeholder="Enter brand name"
            allowClear
        />
      </a-form-item>

      <a-form-item label="Brand Description" name="brandDesc">
        <a-textarea
            v-model:value="brandInfo.brandDesc"
            placeholder="Enter brand description"
            :rows="2"
            allowClear
        />
      </a-form-item>

      <a-form-item label="Brand Story" name="brandStory">
        <a-textarea
            v-model:value="brandInfo.brandStory"
            placeholder="Enter brand story"
            :rows="5"
            allowClear
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts" setup>
import { ref, defineEmits, defineExpose, defineProps } from 'vue'
import { message } from 'ant-design-vue'
import ImageUpload from '@/components/ImageUpload.vue'

// Destructure props for cleaner code
const { queryList, saveOrUpdate } = defineProps({
  queryList: Function,
  saveOrUpdate: Function,
})

const open = ref(false)
const isUpdate = ref(false)
const brandInfo = ref<any>({})
const formRef = ref()
const formLoading = ref(false)
const emit = defineEmits(['queryList'])

const rules = ref({
  brandName: [
    { required: true, message: 'Enter brand name', trigger: ['blur', 'change'] },
    { min: 1, max: 50, message: 'Length must be between 2 and 50 characters', trigger: ['blur', 'change'] },
  ],
  brandDesc: [
    { required: true, message: 'Enter brand description', trigger: ['blur', 'change'] },
    { min: 1, max: 200, message: 'Length must be between 1 and 100 characters', trigger: ['blur', 'change'] },
  ],
})

// Function to handle form submission
const handleOk = async () => {
  try {
    await formRef.value.validate()
    formLoading.value = true
    await saveOrUpdate?.(brandInfo.value)
    message.success('Operation successful')
    queryList?.()
    open.value = false
  } catch (err) {
    console.error('Error:', err)
  } finally {
    formLoading.value = false
  }
}


// Function to open the modal with brand data
defineExpose({
  openModal: (brand: any = {}) => {
    isUpdate.value = !!brand.id
    brandInfo.value = { ...brand }
    open.value = true
  },
})
</script>
