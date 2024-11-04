<script lang="ts" setup>
import {DeleteOutlined, QuestionCircleOutlined} from "@ant-design/icons-vue"

const {recordName, recordId, showIcon} = defineProps({
  recordName: {
    type: String,
  },
  recordId: {
    type: String,
  },
  showIcon: {
    type: Boolean,
    default: false
  }
})
const emit = defineEmits(['deleteRecordById'])
</script>

<template>
  <a-popconfirm :cancel-text="$t('否')" :ok-text="$t('是')" ok-type="danger"
                @confirm="emit('deleteRecordById', recordId)">
    <template #icon>
      <question-circle-outlined style="color: red"/>
    </template>
    <template #title>
      <div>{{ $t('是否删除该项？') }}</div>
      <a-tag class="my-2" color="red">
        <ToolTip :length="20" :text="recordName"/>
      </a-tag>
    </template>
    <DeleteOutlined v-if="showIcon" style="color: red"/>
    <a-button v-else danger type="link">{{
        $t('删除')
      }}
    </a-button>
  </a-popconfirm>
</template>

<style scoped>

</style>