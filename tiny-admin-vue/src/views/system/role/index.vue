<template>
  <div>
    <div class="p-4">
      <div class="flex mb-4">
        <div class="flex items-center gap-4 mx-auto sm:w-[80%] w-full">
          <Auth :has-permission="'role:add'">
            <a-button type="primary" @click="() => saveOrUpdateRole()">{{
                $t('新增')
              }}
            </a-button>
          </Auth>
          <a-input
              id="keyword"
              v-model:value="searchForm.keyword"
              :placeholder="$t('搜索角色或描述')"
              allow-clear
              autocomplete="off"
              class="text-left"
              type="text"
              @keydown.enter.prevent="queryList"
          >
            <template #prefix>
              <a-switch
                  v-model:checked="searchForm.status"
                  :checked-children="$t('已启用')"
                  :loading="switchLoading"
                  :un-checked-children="$t('已禁用')"
                  class="flex-shrink-0"
                  @change="queryByStatus"
              />
            </template>
            <template #suffix>
              <a-button type="primary" @click="queryList">{{
                  $t('搜索')
                }}
              </a-button>
            </template>
          </a-input>
        </div>
      </div>
      <a-table
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
                    @click="
                    () => {
                      changeStatus(record)
                    }
                  "
                />
              </a-popconfirm>
              <a-button
                  class="text-amber-500 -ml-1"
                  type="link"
                  @click="handleAssignRole(record)"
              >{{ $t('分配权限') }}
              </a-button
              >
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
                          @click="() => saveOrUpdateRole(true, record)"
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
        </template>
      </a-table>
    </div>
    <RoleModal ref="roleModalRef" @query-list="queryList"/>
    <AssignRole ref="assignRoleRef" @query-list="queryList"/>
  </div>
</template>
<script lang="ts" setup>
import {t} from '@/utils/i18n.ts'

import {deleteById, getRolePage, saveOrUpdate} from '@/api/role.ts'
import {DownOutlined, QuestionCircleOutlined} from '@ant-design/icons-vue'
import RoleModal from '@/views/system/role/RoleModal.vue'
import {useDebounceFn} from '@vueuse/core'
import {message} from 'ant-design-vue'
import AssignRole from '@/views/system/role/AssignRole.vue'
import {getMenuTree} from '@/api/menu.ts'
import Auth from '@/components/Auth.vue'

const pagination = ref({
  current: 1,
  pageSize: 10,
  total: 0,
})
const searchForm = ref({
  keyword: '',
  pageNum: pagination.value.current,
  pageSize: pagination.value.pageSize,
  status: true,
})

const roleModalRef = ref<any>()
const switchLoading = ref(false)
const queryByStatus = () => {
  switchLoading.value = true
  queryList()
}
const dataSource = ref([])
const queryList = () => {
  getRolePage(searchForm.value)
      .then((res: any) => {
        dataSource.value = res.records
        pagination.value = {
          current: res.current,
          pageSize: res.size,
          total: res.total,
        }
      })
      .catch((err) => {
        message.error(t('获取角色列表失败') + err.message)
      })
      .finally(() => {
        switchLoading.value = false
      })
}
queryList()
const debounceQuery = useDebounceFn(queryList, 500)
watch(() => searchForm.value.keyword, debounceQuery)

const columns: any = [
  {
    title: t('角色'),
    dataIndex: 'roleName',
    key: 'roleName',
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

const handleTableChange = () => {
}
const confirmChangeStatus = (record: any) => {
  record.loading = true
  saveOrUpdate({id: record.id, status: !record.status})
      .then(() => {
        changeStatus(record)
      })
      .finally(() => {
        record.loading = false
      })
}
const changeStatus = (record: any) => {
  record.status = !record.status
}
const saveOrUpdateRole = (isEdit = false, record = {}) => {
  roleModalRef.value?.showModal(isEdit, isEdit ? record : {})
}
const deleteRole = (id: string) => {
  deleteById(id).then(() => {
    queryList()
    message.success(t('删除成功'))
  })
}
const assignRoleRef = ref()
let treeData
const handleAssignRole = (record: any) => {
  if (!treeData) {
    getMenuTree().then((res: any) => {
      treeData = res
      assignRoleRef.value?.show(record, treeData)
    })
  } else {
    assignRoleRef.value?.show(record, treeData)
  }
}
</script>
