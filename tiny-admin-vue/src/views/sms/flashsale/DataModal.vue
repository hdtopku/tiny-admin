<template>
  <a-modal
      v-model:open="open"
      :title="isUpdate ? $t('更新秒杀活动') : $t('新建秒杀活动')"
      destroy-on-close
  >
    <template #footer>
      <a-button type="primary" @click="open = false">{{ $t('取消') }}</a-button>
      <a-button :loading="loading" type="primary" @click="handleOk">{{
          $t('提交')
        }}
      </a-button>
    </template>
    <a-form :label-col="{ span: 4 }" :model="form" :rules="rules">
      <a-form-item :label="$t('活动名称')" name="activityName">
        <a-input v-model:value="form.activityName"/>
      </a-form-item>
      <a-form-item :label="$t('活动时间')" name="startTime">
        <a-range-picker
            v-model:value="rangeTime"
            :placeholder="[$t('开始时间'), $t('结束时间')]"
            :show-time="{ format: 'HH:mm' }"
            format="YYYY-MM-DD HH:mm:ss"
            @change="onRangeChange"
        />
      </a-form-item>
      <a-form-item :label="$t('备注')">
        <a-textarea
            v-model:value="form.remark"
            :placeholder="$t('备注不对外展示')"
            :rows="3"
            allow-clear
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script lang="ts" setup>
import {t} from '@/utils/i18n.ts'

import dayjs, {Dayjs} from 'dayjs'
import {updateFlashSale} from '@/api/sms/flashSale.ts'
import {message} from 'ant-design-vue'

const open = ref(false)
const isUpdate = ref(false)
const form: Ref<any> = ref({})
const rules: Ref<any> = ref({
  activityName: [
    {
      required: true,
      message: t('请输入活动名称'),
      trigger: ['blur', 'change'],
    },
    {
      min: 2,
      max: 50,
      message: t('长度在 2 到 50 个字符'),
      trigger: ['blur', 'change'],
    },
  ],

  startTime: [
    {
      required: true,
      message: t('请选择活动时间'),
      trigger: ['blur', 'change'],
    },
  ],

  endTime: [
    {
      required: true,
      message: t('请选择活动时间'),
      trigger: ['blur', 'change'],
    },
  ],
})
const rangeTime: Ref<[string, string] | [Dayjs, Dayjs] | undefined> = ref([
  '',
  '',
])
const loading = ref(false)
const onRangeChange = (
    value: [string, string] | [Dayjs, Dayjs],
    dateString: [string, string]
) => {
  form.value.startTime = dateString[0]
  form.value.endTime = dateString[1]
}
const emit = defineEmits(['queryList'])
const handleOk = () => {
  loading.value = true
  updateFlashSale(form.value)
      .then(() => {
        message.success(t('操作成功'))
        emit('queryList')
        open.value = false
      })
      .finally(() => {
        loading.value = false
      })
}
defineExpose({
  openModal: (data: any = {}) => {
    form.value = Object.assign({}, data)
    isUpdate.value = !!data.id
    open.value = true
    if (isUpdate.value) {
      rangeTime.value = [dayjs(data.startTime), dayjs(data.endTime)]
    }
  },
})
</script>
