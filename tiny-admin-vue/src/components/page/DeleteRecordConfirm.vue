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
const emit = defineEmits(['confirm'])
</script>

<template>
  <a-popconfirm cancel-text="No" ok-text="Yes" ok-type="danger"
                @confirm="emit('confirm', recordId)">
    <template #icon>
      <question-circle-outlined style="color: red"/>
    </template>
    <template #title>
      <div>Delete this item?</div>
      <a-tag class="my-2" color="red">
        <ToolTip :length="20" :content="recordName"/>
      </a-tag>
    </template>
    <DeleteOutlined v-if="showIcon" style="color: red"/>
    <a-button v-else danger type="link">Delete</a-button>
  </a-popconfirm>
</template>

<style scoped>

</style>