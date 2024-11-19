<template>
  <a-modal :open="open" @cancel="handleCancel" @ok="handleOk" width="800px">
    <template #title
    >Edit:
      <a-tag color="blue">
        <a-typography-paragraph :copyable="{content: modalName}">
          <ToolTip :content="modalName" :length="50"/>
        </a-typography-paragraph>
      </a-tag>
    </template>
    <template #footer>
      <a-button :loading="loading" type="primary" @click="handleOk">Submit</a-button>
      <a-button @click="handleCancel">Cancel</a-button>
    </template>
    <a-form :label-col="{ span: 3 }" :model="form" :rules="rules">
      <a-form-item :label="'Remark'" name="remark">
        <a-textarea
            v-model:value="form.remark"
            :placeholder="'Please enter remark'"
            :rows="3"
            allow-clear
        ></a-textarea>
      </a-form-item>
      <a-form-item :label="'Sort'" name="sort">
        <a-input-number
            v-model:value="form.sort"
            :max="9999"
            :min="1"
            :placeholder="'Please enter sort (smaller numbers appear first)'"
            :step="1"
            class="w-1/2"
        ></a-input-number>
      </a-form-item>
      <a-form-item :label="'Enabled'" name="enabled">
        <a-switch
            v-model:checked="form.enabled"
            :checked-children="'Enabled'"
            :un-checked-children="'Disabled'"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts" setup>
import {ref} from 'vue'
import {message} from 'ant-design-vue'

const {queryList, saveOrUpdate} = defineProps({
  queryList: Function,
  saveOrUpdate: Function,
})

const open = ref(false)
const form = ref({
  id: null,
  remark: '',
  sort: 9999,
  enabled: true,
})
const rules: any = {}
const handleCancel = () => {
  open.value = false
}
const loading = ref(false)
const emit = defineEmits(['queryList'])
let submit: Function
const handleOk = () => {
  loading.value = true
  const {id, remark, sort, enabled} = form.value
  submit({id, remark, sort, enabled})
      .then(() => {
        message.success('Success')
        open.value = false
        queryList()
      })
      .finally(() => {
        loading.value = false
      })
}
const modalName = ref('')
defineExpose({
  openModal: (goods: any = {}, submitFunc: Function) => {
    open.value = true
    const {createTime, updateTime, productName, ...rest} = goods
    modalName.value = productName
    form.value = Object.assign({}, rest)
    submit = submitFunc
  }
})
</script>
