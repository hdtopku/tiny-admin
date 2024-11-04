<script lang="ts" setup>
import {QuestionCircleOutlined} from "@ant-design/icons-vue";

const {record} = defineProps<{ record: any }>()
const emit = defineEmits(['changeRecordStatus'])
</script>

<template>
  <a-popconfirm :cancel-text="$t('否')" :ok-text="$t('是')"
                :title="record.status ? $t('是否禁用该项？') : $t(' 是否启用该项？')"
                @confirm="emit('changeRecordStatus', record)">
    <template #icon>
      <question-circle-outlined style="color: red"/>
    </template>
    <a-switch
        v-model:checked="record.status"
        :checked-children="$t('已启用')"
        :loading="record.loading"
        :un-checked-children="$t('已禁用')"
        class="flex-shrink-0"
        size="small"
        @click="record.status = !record.status"/>
  </a-popconfirm>
</template>