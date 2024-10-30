<template>
  <div>
    <a-modal
        v-model:open="open"
        :cancel-text="$t('取消')"
        :ok-text="$t('提交')"
        :title="isUpdate ? $t('编辑推荐品牌') : $t('新增推荐品牌')"
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
          v-show="activeKey === 'basicInfo'"
          ref="formRef"
          :label-col="{ span: 4 }"
          :model="brandInfo"
          :rules="rules"
      >
        <a-form-item :label="$t('选择品牌')" name="brandId">
          <a-select
              v-model:value="brandInfo.brandId"
              :filter-option="filterOption"
              :options="options"
              :placeholder="$t('请选择品牌')"
              show-search
              style="width: 200px"
          ></a-select>
        </a-form-item>
        <div class="grid grid-cols-2 justify-center gap-4">
          <a-form-item :label="$t('排序')" :label-col="{ span: 8 }" name="sort">
            <a-input-number
                v-model:value="brandInfo.sort"
                :max="99999999"
                :min="0"
                default-value="9999"
            />
          </a-form-item>
          <a-form-item
              :label="$t('是否启用')"
              :label-col="{ span: 8 }"
              name="status"
          >
            <a-switch
                v-model:checked="brandInfo.status"
                :checked-value="1"
                :un-checked-value="0"
            />
          </a-form-item>
        </div>
        <a-form-item :label="$t('备注')" name="remark">
          <a-textarea
              v-model:value="brandInfo.remark"
              :placeholder="$t('备注信息仅在后台管理显示，不会对用户显示')"
              :rows="3"
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
import {message, SelectProps} from 'ant-design-vue'
import {saveOrUpdateSmsBrand} from '@/api/sms/brand.ts'
import {getAllBrands} from "@/api/pms/brand.ts";

const open = ref<boolean>(false)
const isUpdate = ref<boolean>(false)
const brandInfo = ref<any>()
const rules: Ref<any> = ref({
  brandId: [
    {required: true, message: t('请选择品牌'), trigger: ['blur', 'change']},
  ],
})
const formRef = ref()
const formLoading = ref(false)
const emits = defineEmits(['queryList'])
const handleOk = () => {
  formRef.value
      .validate()
      .then(() => {
        formLoading.value = true
        const {id, brandId, sort, status, remark} = brandInfo.value
        saveOrUpdateSmsBrand({id, brandId, sort, status, remark})
            .then(() => {
              message.success(t('操作成功'))
              open.value = false
              emits('queryList')
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
  brandInfo.value = Object.assign({}, {...{brandId: null, status: 1, sort: 9999, remark: '',}, ...brand})
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
