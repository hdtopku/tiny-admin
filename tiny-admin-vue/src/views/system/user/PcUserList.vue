<script lang="ts" setup>
import {DownOutlined, QuestionCircleOutlined} from "@ant-design/icons-vue"
import {deleteUser, saveOrUpdate} from "@/api/system/user.ts";
import {message, Pagination} from "ant-design-vue";
import {t} from "@/utils/i18n.ts";

const {dataSource, pagination} = defineProps({
  dataSource: Array,
  pagination: Pagination,
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

const emit = defineEmits(['openModal', 'openPasswordModal', 'queryList'])
const openModal = (record: any) => {
  emit('openModal', record)
}
const handleChangePassword = (record: any) => {
  emit('openPasswordModal', record)
}
const handleDeleteUser = (id: string) => {
  deleteUser(id).then(() => {
    // queryList()
    message.success(t('删除成功'))
  })
}
const handleTableChange = (pagination: any) => {
  emit('queryList', {
    pageNum: pagination.current,
    pageSize: pagination.pageSize,
  })
}

const confirmChangeStatus = (record: any) => {
  record.loading = true
  const {loading, ...rest} = record
  rest.status = !rest.status
  saveOrUpdate(rest)
      .then(() => {
        record.status = rest.status
        message.success(t('操作成功'))
      })
      .finally(() => {
        record.loading = false
      })
}
</script>

<template>

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
            <a-switch v-model:checked="record.status" :checked-children="$t('已启用')" :loading="record.loading"
                      :un-checked-children="$t('已禁用')"
                      class="flex-shrink-0" size="small"
                      @click="() => record.status = !record.status"/>
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
                      @click="() => openModal(record)"
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

</template>