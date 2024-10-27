<script lang="ts" setup>
import {message, Pagination} from "ant-design-vue";
import {DeleteOutlined, EditOutlined, QuestionCircleOutlined} from "@ant-design/icons-vue";
import {deleteRoleById, saveOrUpdateRole} from "@/api/system/role.ts";

const {dataSource, pagination, loading} = defineProps({
  dataSource: Array,
  pagination: Pagination,
  loading: Boolean
})
const emit = defineEmits(['openModal', 'queryList', 'openAssignRoleModal'])
const confirmChangeStatus = (record: any) => {
  record.status = !record.status
  saveOrUpdateRole(record).then(() => {
    emit('queryList')
  })
}
const openModal = (record: any) => {
  emit('openModal', record)
}
const handlePageChange = (pageNum: number, pageSize: number) => {
  emit('queryList', {pageNum, pageSize})
}
const deleteRecord = (id: number) => {
  deleteRoleById(id).then(() => {
    message.success('删除成功')
    emit('queryList')
  })
}
</script>

<template>
  <a-list :data-source="dataSource" :loading="loading" :pagination="{...pagination, onChange: handlePageChange}">
    <template #renderItem="{ item:record }">
      <a-list-item>
        <a-card class="w-full">
          <template #actions>
            <a-popconfirm
                :cancel-text="$t('否')"
                :ok-text="$t('是')"
                :title="
                record.status ? $t('是否禁用该角色？') : $t(' 是否启用该角色？')
              "
                @confirm="confirmChangeStatus(record)"
            >
              <template #icon>
                <question-circle-outlined style="color: red"/>
              </template>
              <a-switch
                  v-model:checked="record.status"
                  :checked-children="$t('已启用')"
                  :loading="record.loading"
                  :un-checked-children="$t('已禁用')"
                  class="!mt-2"
                  @click="() => {record.status =!record.status}"
              />
            </a-popconfirm>
            <a-popconfirm
                :cancel-text="$t('否')"
                :ok-text="$t('是')"
                ok-type="danger"
                @confirm="deleteRecord(record.id)"
            >
              <template #icon>
                <question-circle-outlined style="color: red"/>
              </template>
              <template #title>
                <div>{{ $t('是否删除角色？') }}</div>
                <a-tag class="my-2" color="red">{{
                    record.roleName
                  }}
                </a-tag>
              </template>
              <a-button danger type="link">
                <DeleteOutlined/>
              </a-button>
            </a-popconfirm>
            <a-button type="link" @click="() => openModal(record)">
              <EditOutlined/>
            </a-button>

          </template>
          <a-card-meta>
            <template #title>
              <div class="flex justify-between items-center">
                {{ record.roleName }}
                <div>
                  <a-tag>已拥有{{ record.menus.length }}个权限</a-tag>
                  <a-button class="text-amber-500 -ml-1" type="link" @click="emit('openAssignRoleModal', record)">
                    {{ $t('分配权限') }}
                  </a-button>
                </div>
              </div>
            </template>
            <template #description>
              <a-typography-text>{{ record.description }}</a-typography-text>
            </template>
          </a-card-meta>
        </a-card>
      </a-list-item>
    </template>
  </a-list>
</template>