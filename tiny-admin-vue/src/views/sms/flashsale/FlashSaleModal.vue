<script lang="ts" setup>
import * as dayjs from "dayjs"
import {Dayjs} from "dayjs"
import {saveOrUpdateFlashSale} from "@/api/sms/flashSale.ts";
import {message} from "ant-design-vue";

const open = ref(false)
const isUpdate = ref(false)
const form: Ref<any> = ref({})
const rules: Ref<any> = ref({
  activityName: [
    {required: true, message: '请输入活动名称', trigger: ['blur', 'change']},
    {min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: ['blur', 'change']}
  ],
  startTime: [
    {required: true, message: '请选择活动时间', trigger: ['blur', 'change']}
  ],
  endTime: [
    {required: true, message: '请选择活动时间', trigger: ['blur', 'change']}
  ],
})
const rangeTime: Ref<[string, string] | [Dayjs, Dayjs] | undefined> = ref(['', ''])
const loading = ref(false)
const onRangeChange = (value: [string, string] | [Dayjs, Dayjs], dateString: [string, string]) => {
  form.value.startTime = dateString[0]
  form.value.endTime = dateString[1]
}
const emit = defineEmits(['queryList'])
const handleOk = () => {
  loading.value = true
  saveOrUpdateFlashSale(form.value).then(() => {
    message.success('操作成功')
    emit('queryList')
    open.value = false
  }).finally(() => {
    loading.value = false
  })
}
defineExpose({
  showModal: (data: any = {}) => {
    form.value = Object.assign({}, data)
    isUpdate.value = !!data.id
    open.value = true
    if (isUpdate.value) {
      rangeTime.value = [dayjs(data.startTime), dayjs(data.endTime)]
    }
  }
})
</script>

<template>
  <a-modal v-model:open="open" :title="isUpdate ? '更新秒杀活动' : '新建秒杀活动'" destroy-on-close>
    <template #footer>
      <a-button type="primary" @click="open = false">取消</a-button>
      <a-button :loading="loading" type="primary" @click="handleOk">提交</a-button>
    </template>
    <a-form :label-col="{ span: 4 }" :model="form" :rules="rules">
      <a-form-item label="活动名称" name="activityName">
        <a-input v-model:value="form.activityName"/>
      </a-form-item>
      <a-form-item label="活动时间" name="startTime">
        <a-range-picker
            v-model:value="rangeTime"
            :placeholder="['开始时间', '结束时间']"
            :show-time="{ format: 'HH:mm' }"
            format="YYYY-MM-DD HH:mm:ss"
            @change="onRangeChange"
        />
      </a-form-item>
      <a-form-item label="备注">
        <a-textarea v-model:value="form.remark" :rows="3" allow-clear placeholder="备注不对外展示"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<style scoped>

</style>