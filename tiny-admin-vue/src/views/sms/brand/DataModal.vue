<template>
  <div>
    <a-modal
        v-model:open="open"
        :cancel-text="'Cancel'"
        :ok-text="'Submit'"
        :title="isUpdate ? 'Edit Recommended Brand' : 'Add Recommended Brand'"
        destroy-on-close
        @ok="handleOk"
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
          :label-col="{ span: 4 }"
          :model="brandInfo"
          :rules="rules"
      >
        <a-form-item :label="'Select Brand'" name="brandId">
          <a-select
              v-model:value="brandInfo.brandId"
              :filter-option="filterOption"
              :options="options"
              :placeholder="'Please select a brand'"
              show-search
              style="width: 200px"
          ></a-select>
        </a-form-item>
        <div class="grid grid-cols-2 justify-center gap-4">
          <a-form-item :label="'Sort Order'" :label-col="{ span: 8 }" name="sort">
            <a-input-number
                v-model:value="brandInfo.sort"
                :max="99999999"
                :min="0"
                default-value="9999"
            />
          </a-form-item>
          <a-form-item
              :label="'Is Enabled'"
              :label-col="{ span: 8 }"
              name="enabled"
          >
            <a-switch
                :checked-children="'Enabled'"
                :un-checked-children="'Disabled'"
                v-model:checked="brandInfo.enabled"
            />
          </a-form-item>
        </div>
        <a-form-item :label="'Remark'" name="remark">
          <a-textarea
              v-model:value="brandInfo.remark"
              :placeholder="'Remarks are displayed in the backend and will not be shown to users.'"
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
import {message, SelectProps} from 'ant-design-vue'
import {getAllBrands} from "@/api/pms/brand.ts";

const {saveOrUpdate, queryList} = defineProps({
  queryList: Function,
  saveOrUpdate: Function,
})

const open = ref<boolean>(false)
const isUpdate = ref<boolean>(false)
const brandInfo = ref<any>()
const rules: Ref<any> = ref({
  brandId: [
    {required: true, message: 'Please select a brand', trigger: ['blur', 'change']},
  ],
})
const formRef = ref()
const formLoading = ref(false)
const handleOk = () => {
  formRef.value
      .validate()
      .then(() => {
        formLoading.value = true
        const {id, brandId, sort, enabled, remark} = brandInfo.value
        saveOrUpdate({id, brandId, sort, enabled, remark})
            .then(() => {
              message.success('Success')
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
const activeKey = ref('basicInfo')
const options = ref<SelectProps['options']>([])
const filterOption = (input: string, option: any) => {
  return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0
}
let brandList: any = [], brandMap = new Map()
const openModal = (brand: any = {}) => {
  open.value = true
  if (!brandList.length) {
    getAllBrands().then((res: any) => {
      brandList = res
      res.forEach((item: any) => {
        brandMap.set(item.id, item)
        item.brandId = item.id
        item.disabled = false
      })
      openModal(brand)
      return
    })
  }
  isUpdate.value = !!brand.id
  brandInfo.value = Object.assign({}, {...{brandId: null, enabled: true, sort: 9999, remark: '',}, ...brand})
  options.value = brandList.map((item) => ({
    label: item.brandName,
    value: item.brandId,
    disabled: item.disabled ?? true,
  }))
}
defineExpose({
  openModal,
})
</script>
