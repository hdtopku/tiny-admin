<template>
  <a-modal :open="open" @cancel="handleCancel" @ok="handleOk">
    <template #title
    >{{
        $t('编辑推荐新品id：')
      }}
      <a-tag color="blue">
        <a-typography-text copyable>
          {{ form.goodsId }}
        </a-typography-text>
      </a-tag>
    </template>
    <template #footer>
      <a-button :loading="loading" type="primary" @click="handleOk">{{
          $t('提交')
        }}
      </a-button>
      <a-button @click="handleCancel">{{ $t('取消') }}</a-button>
    </template>
    <a-form :label-col="{ span: 3 }" :model="form" :rules="rules">
      <a-form-item :label="$t('备注')" name="remark">
        <a-textarea
            v-model:value="form.remark"
            :placeholder="$t('请输入备注')"
            :rows="3"
            allow-clear
        ></a-textarea>
      </a-form-item>
      <a-form-item :label="$t('排序')" name="sort">
        <a-input-number
            v-model:value="form.sort"
            :max="9999"
            :min="1"
            :placeholder="$t('请输入排序(数值越小越靠前)')"
            :step="1"
            class="w-1/2"
        ></a-input-number>
      </a-form-item>
      <a-form-item :label="$t('状态')" name="status">
        <a-switch
            v-model:checked="form.status"
            :checked-children="$t('已启用')"
            :checked-value="1"
            :un-checked-children="$t('已禁用')"
            :un-checked-value="0"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script lang="ts" setup>
import {t} from '@/utils/i18n.ts'

import {updateNewGoods} from '@/api/sms/newGoods.ts'
import {message} from 'ant-design-vue'

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
    {required: true, message: t('请输入备注'), trigger: ['blur', 'change']},
  ],
}
const handleCancel = () => {
  open.value = false
}
const loading = ref(false)
const emit = defineEmits(['queryList'])
const handleOk = () => {
  loading.value = true
  updateNewGoods(form.value)
      .then(() => {
        message.success(t('修改成功'))
        open.value = false
        emit('queryList')
      })
      .finally(() => {
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
