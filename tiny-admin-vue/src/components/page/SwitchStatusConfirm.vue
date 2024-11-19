<script lang="ts" setup>
import {QuestionCircleOutlined} from "@ant-design/icons-vue";

const {record} = defineProps<{ record: any }>()
const emit = defineEmits(['confirm'])
</script>

<template>
  <a-popconfirm cancel-text="No" ok-text="Yes"
                :title="record.enabled ? 'Disable this item?' : 'Enable this item?'"
                @confirm="emit('confirm', record)">
    <template #icon>
      <question-circle-outlined style="color: red"/>
    </template>
    <a-switch
        v-model:checked="record.enabled"
        checked-children="Enabled"
        un-checked-children="Disabled"
        :loading="record.loading"
        class="flex-shrink-0"
        size="small"
        @click="record.enabled = !record.enabled"/>
  </a-popconfirm>
</template>
