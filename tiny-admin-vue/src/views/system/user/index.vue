<template>
  <div>
    <div class="p-4">
      <div class="flex mb-4">
        <div class="flex items-center gap-4 mx-auto sm:w-[80%] w-full">
          <a-button type="primary" @click="saveOrUpdateUserInfo">{{
              $t('新增')
            }}
          </a-button>
          <a-input
              id="keyword"
              v-model:value="searchForm.keyword"
              :placeholder="$t('搜索用户名、昵称、邮箱、手机号')"
              allow-clear
              autocomplete="off"
              class="text-left"
              type="text"
              @keyup.enter.native="queryList"
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
          :scroll="{ x: 'max-content', y: 'calc(100vh - 200px)' }"
          @change="handleTableChange"
      >
        <template #bodyCell="{ record, index, column }">
          <template v-if="column.key === 'index'">
            {{ index + 1 }}
          </template>
          <template v-if="column.dataIndex === 'avatar'">
            <a-avatar :src="record.avatar" shape="square" size="large"/>
          </template>
          <template v-if="column.dataIndex === 'roleNames'">
            <span v-if="record.roleNames?.length">
              <a-tag v-for="item in record.roleNames" :key="item">
                {{ item }}
              </a-tag>
            </span>
            <span v-else class="text-gray-400 text-xs">{{
                $t('暂未分配角色')
              }}</span>
          </template>
          <template v-if="column.dataIndex === 'status'">
            <div class="grid grid-cols-2 items-center justify-center">
              <a-popconfirm
                  :cancel-text="$t('否')"
                  :ok-text="$t('是')"
                  :title="
                  record.status
                    ? $t('是否禁用该用户？')
                    : $t(' 是否启用该用户？')
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
                          @click="() => saveOrUpdateUserInfo(record, true)"
                      >{{ $t('编辑用户') }}
                      </a-button
                      >
                    </a-menu-item>
                    <a-menu-item>
                      <a-button
                          class="text-orange-300"
                          type="link"
                          @click="handleChangePassword(record)"
                      >{{ $t('重置密码') }}
                      </a-button
                      >
                    </a-menu-item>
                    <a-menu-item>
                      <a-button type="link">{{ $t('更换头像') }}</a-button>
                    </a-menu-item>
                    <a-menu-item>
                      <a-popconfirm
                          :cancel-text="$t('否')"
                          :ok-text="$t('是')"
                          ok-type="danger"
                          @confirm="
                          () => {
                            handleDeleteUser(record.key)
                          }
                        "
                      >
                        <template #icon>
                          <question-circle-outlined style="color: red"/>
                        </template>
                        <template #title>
                          <div>{{ $t('是否删除用户？') }}</div>
                          <a-tag class="my-2" color="red"
                          >{{ record.username }}_{{ record.nickname }}
                          </a-tag
                          >
                        </template>
                        <a-button danger type="link">{{
                            $t('删除用户')
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
    <UserModal ref="UserModalRef" @queryList="queryList"/>
    <UserPasswordModal ref="UserPasswordModalRef"/>
  </div>
</template>
<script lang="ts" setup>
import {t} from '@/utils/i18n.ts'

import {deleteUser, getUserPage, saveOrUpdate} from '@/api/system/user.ts'
import {useDebounceFn} from '@vueuse/core'
import {DownOutlined, QuestionCircleOutlined} from '@ant-design/icons-vue'
import {message} from 'ant-design-vue'
import {getRoleList} from '@/api/system/role.ts'

const UserModal = defineAsyncComponent(
    () => import('@/views/system/user/UserModal.vue')
)
const UserPasswordModal = defineAsyncComponent(
    () => import('@/views/system/user/UserPasswordModal.vue')
)

const pagination = ref({
  current: 1,
  pageSize: 10,
  total: 0,
})
const searchForm = ref({
  keyword: '',
  status: true,
  pageNum: pagination.value.current,
  pageSize: pagination.value.pageSize,
})
const columns: any = [
  {
    title: t('序号'),
    key: 'index',
    width: 60,
  },
  {
    title: t('头像'),
    dataIndex: 'avatar',
    key: 'avatar',
    width: 80,
  },
  {
    title: t('用户名'),
    dataIndex: 'username',
    key: 'username',
    width: 80,
  },
  {
    title: t('用户昵称'),
    dataIndex: 'nickname',
    key: 'nickname',
    width: 120,
  },
  {
    title: t('角色列表'),
    dataIndex: 'roleNames',
    key: 'roleNames',
    width: 120,
  },
  {
    title: t('邮箱'),
    dataIndex: 'email',
    key: 'email',
    width: 60,
  },
  {
    title: t('手机号'),
    dataIndex: 'phone',
    key: 'phone',
    width: 80,
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
    width: 160,
  },
]

const dataSource = ref([])
const switchLoading = ref(false)
const queryList = () => {
  getUserPage(searchForm.value)
      .then((res: any) => {
        dataSource.value = res.records
        pagination.value = {
          current: res.current,
          pageSize: res.size,
          total: res.total,
        }
      })
      .finally(() => {
        switchLoading.value = false
      })
}
queryList()
const handleTableChange = (pagination: any) => {
  searchForm.value.pageNum = pagination.current
  searchForm.value.pageSize = pagination.pageSize
  queryList()
}
const debounceQuery = useDebounceFn(queryList, 500)
watch(() => searchForm.value.keyword, debounceQuery)
const changeStatus = (record: any) => {
  record.status = !record.status
}

const confirmChangeStatus = (record: any) => {
  record.loading = true
  saveOrUpdate({id: record.key, status: !record.status})
      .then(() => {
        changeStatus(record)
      })
      .finally(() => {
        record.loading = false
      })
}
const queryByStatus = () => {
  switchLoading.value = true
  queryList()
}

const defaultUserInfo = {
  username: '',
  nickname: '',
  email: '',
  phone: '',
}
const UserModalRef = ref()
let roleList: any
const saveOrUpdateUserInfo = (record: any, isEdit = false) => {
  if (!roleList) {
    getRoleList().then((res: any) => {
      roleList = res
      UserModalRef.value.showModal({
        roles: roleList,
        isEdit,
        userInfo: {...(isEdit ? record : defaultUserInfo)},
      })
    })
  } else {
    UserModalRef.value.showModal({
      roles: roleList,
      isEdit,
      userInfo: {...(isEdit ? record : defaultUserInfo)},
    })
  }
}
const UserPasswordModalRef = ref()
const handleChangePassword = (record: any) => {
  UserPasswordModalRef.value.showModal(record)
}
const handleDeleteUser = (id: string) => {
  deleteUser(id).then(() => {
    queryList()
    message.success(t('删除成功'))
  })
}
</script>
