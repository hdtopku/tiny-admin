<template>
  <a-modal
      v-model:open="open"
      :title="isUpdate ? 'Update Flash Sale Event' : 'Create Flash Sale Event'"
      destroy-on-close
      width="800px"
  >
    <template #footer>
      <a-button type="primary" @click="open = false">Cancel</a-button>
      <a-button :loading="loading" type="primary" @click="handleOk">Submit</a-button>
    </template>
    <a-form :label-col="{ span: 6 }" :model="form" :rules="rules">
      <a-form-item label="Event Name" name="activityName">
        <a-input v-model:value="form.activityName"/>
      </a-form-item>
      <a-form-item label="Event Time" name="startTime">
        <a-range-picker
            v-model:value="rangeTime"
            :placeholder="['Start Time', 'End Time']"
            :show-time="{ format: 'HH:mm' }"
            format="YYYY-MM-DD HH:mm:ss"
            @change="onRangeChange"
        />
      </a-form-item>
      <a-form-item label="Remarks">
        <a-textarea
            v-model:value="form.remark"
            placeholder="Remarks are not publicly displayed"
            :rows="3"
            allow-clear
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script lang="ts" setup>
import dayjs, {Dayjs} from 'dayjs'
import {message} from 'ant-design-vue'

const {queryList = () => {}, saveOrUpdate = () => {}} = defineProps({
  queryList: Function,
  saveOrUpdate: Function,
})
const open = ref(false), isUpdate = ref(false), form: Ref<any> = ref({}), loading = ref(false)
const rangeTime: Ref<[Dayjs, Dayjs] | undefined> = ref([
  dayjs().add(1, 'hour'),
  dayjs().add(2, 'hour'),
])

const onRangeChange = (
    value: [string, string] | [Dayjs, Dayjs],
    dateString: [string, string]
) => {
  form.value.startTime = dateString[0]
  form.value.endTime = dateString[1]
}
const emit = defineEmits(['queryList'])
const handleOk = async () => {
  loading.value = true
  try {
    form.value.startTime = rangeTime.value[0].format('YYYY-MM-DD HH:mm:ss')
    form.value.endTime = rangeTime.value[1].format('YYYY-MM-DD HH:mm:ss')
    console.log(form.value)
    await saveOrUpdate(form.value)
    message.success('Success')
    queryList()
    open.value = false
  } catch (error) {
    // 你可以在这里处理错误，例如显示一个错误消息
    console.error(error)
  } finally {
    loading.value = false
  }
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

const rules: Ref<any> = ref({
  activityName: [
    {
      required: true,
      message: 'Please enter the event name',
      trigger: ['blur', 'change'],
    },
    {
      min: 2,
      max: 50,
      message: 'Length must be between 2 and 50 characters',
      trigger: ['blur', 'change'],
    },
  ],

  startTime: [
    {
      required: true,
      message: 'Please select the event time',
      trigger: ['blur', 'change'],
    },
  ],

  endTime: [
    {
      required: true,
      message: 'Please select the event time',
      trigger: ['blur', 'change'],
    },
  ],
})
</script>
