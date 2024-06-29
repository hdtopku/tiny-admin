<script lang="ts" setup>
import {assignRoles} from "@/api/user.ts";
import {message} from "ant-design-vue";

const visible = ref(false)
const curUser: any = ref()
let roleList: any = ref([])

const selectedRoleNames = ref<string[]>([]);

const filteredOptions = computed(() => {
  return roleList.value.filter((item: any) => !selectedRoleNames.value.includes(item.roleName)).map(item => ({
    value: item.roleName,
    label: item.roleName
  }))
})
const emit = defineEmits(['queryList'])
const handleOk = () => {
  const selectRoleIds = roleList.value.filter(item => selectedRoleNames.value.includes(item.roleName)).map(item => item.id)
  assignRoles(curUser.value.key, selectRoleIds).then(() => {
    message.success("角色分配成功")
    visible.value = false
    emit('queryList')
  }).catch((err) => {
    message.error("角色分配失败" + err)
  })
}
const handleCancel = () => {
  visible.value = false
}

defineExpose({
  showModal(user: any, roles: any) {
    visible.value = true
    curUser.value = user
    roleList.value = roles
    selectedRoleNames.value = user.roleNames
  }
})
</script>

<template>
  <a-modal :closable="false" :open="visible" cancel-text="取消" ok-text="提交" title="分配角色"
           width="500px" @cancel="handleCancel" @ok="handleOk">
    <a-form>
      <a-form-item label="角色"
      >
        <a-select key="id" v-model:value="selectedRoleNames" :options="filteredOptions" allow-clear
                  mode="multiple" placeholder="请选择角色">
        </a-select>
      </a-form-item>
    </a-form>
  </a-modal>
</template>
