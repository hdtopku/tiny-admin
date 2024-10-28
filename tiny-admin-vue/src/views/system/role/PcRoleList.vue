<script lang="ts" setup>
import {DownOutlined, QuestionCircleOutlined} from "@ant-design/icons-vue"
import {message, Pagination} from "ant-design-vue";
import {t} from "@/utils/i18n.ts";
import {deleteRoleById, saveOrUpdateRole} from "@/api/system/role.ts";

const {dataSource, pagination, loading} = defineProps({
  dataSource: Array,
  pagination: Pagination,
  loading: Boolean,
})

const emit = defineEmits(['openModal', 'openAssignRoleModal', 'queryList'])
const confirmChangeStatus = (record: any) => {
  record.loading = true
  saveOrUpdateRole({id: record.id, status: !record.status})
      .then(() => {
        record.status = !record.status
        message.success(t('操作成功'))
      })
      .finally(() => {
        record.loading = false
      })
}
const handleTableChange = (pagination: any) => {
  emit('queryList', {pageNum: pagination.current, pageSize: pagination.pageSize,})
}
const deleteRole = (id: string) => {
  deleteRoleById(id).then(() => {
    emit('queryList')
    message.success(t('删除成功'))
  })
}
const columns: any = [
  {
    title: t('角色'),
    dataIndex: 'roleName',
    key: 'roleName',
  },
  {
    title: '权限',
    dataIndex: 'roleSize',
    key: 'roleSize',
  },
  {
    title: t('描述'),
    dataIndex: 'description',
    key: 'description',
  },
  {
    title: t('创建时间'),
    dataIndex: 'createTime',
    key: 'createTime',
  },
  {
    title: t('更新时间'),
    dataIndex: 'updateTime',
    key: 'updateTime',
  },
  {
    title: t('操作'),
    dataIndex: 'status',
    key: 'status',
    fixed: 'right',
    width: 240,
  },
]
</script>

<template>
  <div>
    <a-table
        :loading="loading"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="pagination"
        :scroll="{ x: 'max-content', y: 'calc(100vh - 300px)' }"
        @change="handleTableChange"
    >
      <template #bodyCell="{ record, column }">
        <template v-if="column.dataIndex === 'status'">
          <div class="grid grid-cols-3 items-center">
            <a-popconfirm
                :cancel-text="$t('否')"
                :ok-text="$t('是')"
                :title="
                  record.status
                    ? $t('是否禁用该角色？')
                    : $t(' 是否启用该角色？')
                "
                @confirm="
                  () => {
                    confirmChangeStatus(record)
                  }
                "
            >
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
                  @click="record.status = !record.status"
              />
            </a-popconfirm>
            <a-button class="text-amber-500 -ml-1" type="link" @click="emit('openAssignRoleModal', record)">
              {{ $t('分配权限') }}
            </a-button>
            <a-dropdown>
              <a-button class="flex items-center" size="small" type="link"
              >{{ $t('操作') }}
                <DownOutlined/>
              </a-button>
              <template #overlay>
                <a-menu class="text-center">
                  <a-menu-item>
                    <a-button
                        type="link"
                        @click="() => emit('openModal', record)"
                    >{{ $t('编辑角色') }}
                    </a-button
                    >
                  </a-menu-item>
                  <a-menu-item>
                    <a-popconfirm
                        :cancel-text="$t('否')"
                        :ok-text="$t('是')"
                        ok-type="danger"
                        @confirm="
                          () => {
                            deleteRole(record.id)
                          }
                        "
                    >
                      <template #icon>
                        <question-circle-outlined style="color: red"/>
                      </template>
                      <template #title>
                        <div>{{ $t('是否删除角色：') }}</div>
                        <a-tag class="my-2" color="red">{{
                            record.roleName
                          }}
                        </a-tag>
                      </template>
                      <a-button danger type="link">{{
                          $t('删除角色')
                        }}
                      </a-button>
                    </a-popconfirm>
                  </a-menu-item>
                </a-menu>
              </template>
            </a-dropdown>
          </div>
        </template>
        <template v-if="column.dataIndex === 'roleSize'">
          {{ record.menus.length }}个
        </template>
      </template>
    </a-table>
  </div>
</template>

<style scoped>

</style>