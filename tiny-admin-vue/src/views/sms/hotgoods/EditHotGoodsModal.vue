<script lang="ts" setup>
import {message} from "ant-design-vue";
import {updateHotGoods} from "@/api/sms/hotGoods.ts";

const open = ref(false)
const form = ref({
  id: null,
  goodsId: null,
  remark: '',
  sort: 9999,
  status: 1,
})
const rules: any = {
  remark: [
    {required: true, message: '请输入备注', trigger: ['blur', 'change']},
  ]
}
const handleCancel = () => {
  open.value = false
}
const loading = ref(false)
const emit = defineEmits(['queryList'])
const handleOk = () => {
  loading.value = true
  updateHotGoods(form.value).then(() => {
    message.success('修改成功')
    open.value = false
    emit('queryList')
  }).finally(() => {
    loading.value = false
  })
}
const showModal = (goods: any) => {
  open.value = true
  const {createTime, updateTime, ...rest} = goods
  form.value = Object.assign({}, rest)
}
defineExpose({
  showModal,
})
</script>

<template>
  <a-modal :open="open" @cancel="handleCancel" @ok="handleOk">
    <template #title>
      编辑热销商品id：
      <a-tag color="blue">
        <a-typography-text copyable>
          {{ form.goodsId }}
        </a-typography-text>
      </a-tag>
    </template>
    <template #footer>
      <a-button :loading="loading" type="primary" @click="handleOk">
        提交
      </a-button>
      <a-button @click="handleCancel">
        取消
      </a-button>
    </template>
    <a-form :label-col="{span: 3}" :model="form" :rules="rules">
      <a-form-item label="备注" name="remark">
        <a-textarea v-model:value="form.remark" :rows="3" allow-clear placeholder="请输入备注"></a-textarea>
      </a-form-item>
      <a-form-item label="排序" name="sort">
        <a-input-number v-model:value="form.sort" :max="9999" :min="1" :step="1" class="w-1/2"
                        placeholder="请输入排序(数值越小越靠前)"></a-input-number>
      </a-form-item>
      <a-form-item label="状态" name="status">
        <a-switch v-model:checked="form.status" :checked-value="1" :un-checked-value="0"
                  checked-children="已启用" un-checked-children="已禁用"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<style scoped>

</style>