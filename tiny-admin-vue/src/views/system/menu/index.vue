<template>
  <div>
    <div class="p-4">
      <div class="flex mb-4">
        <div class="flex items-center gap-4 mx-auto sm:w-[80%] w-full">
          <Auth :has-permission="'menu:add'">
            <a-button type="primary" @click="() => saveOrUpdate()">{{
                $t('新增')
              }}
            </a-button>
          </Auth>
          <a-input
              id="keyword"
              v-model:value="searchForm.keyword"
              :placeholder="$t('搜索菜单名称')"
              allow-clear
              autocomplete="off"
              class="text-left"
              type="text"
              @keydown.enter.prevent="queryList"
          >
            <template #prefix>
              <a-switch
                  :checked-children="$t('已启用')"
                  :loading="switchLoading"
                  :un-checked-children="$t('已禁用')"
                  class="flex-shrink-0"
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
          :data-source="dataSource"
          :loading="switchLoading"
          :scroll="{ x: 'max-content', y: 'calc(100vh - 200px)' }"
          row-key="id"
      >
        <template #bodyCell="{ record, column }">
          <template v-if="column.dataIndex === 'name'">
            <CreateIcon :icon="record.icon"/>
            {{ record.name }}
          </template>
          <template v-if="column.dataIndex === 'type'">
            <template v-if="record.type === 0">{{ $t('目录') }}</template>
            <template v-if="record.type === 1">{{ $t('菜单') }}</template>
            <template v-if="record.type === 2">{{ $t('按钮') }}</template>
          </template>
          <template v-if="column.key === 'operation'">
            <a-button type="link" @click="saveOrUpdate(record, true)">{{
                $t('修改')
              }}
            </a-button>
            <Auth :has-permission="'system:sysmenu:deletebyids'">
              <a-popconfirm
                  :cancel-text="$t('否')"
                  :ok-text="$t('是')"
                  :title="$t('是否删除该菜单？')"
                  ok-type="danger"
                  @confirm="() => deleteMenu(record.id)"
              >
                <template #icon>
                  <question-circle-outlined style="color: red"/>
                </template>
                <a-button danger type="link">{{ $t('删除') }}</a-button>
              </a-popconfirm>
            </Auth>
            <a-button
                v-show="record.type === 1"
                link
                type="link"
                @click="() => saveOrUpdate({ parentId: record.id }, false)"
            >{{ $t('新增') }}
            </a-button
            >
          </template>
        </template>
      </a-table>
    </div>
    <MenuModal
        ref="menuModalRef"
        :menuTree="dataSource"
        @query-list="queryList"
    />
  </div>
</template>
<script lang="ts" setup>
import {t} from '@/utils/i18n.ts'

import {deleteMenuById, getMenuTree} from '@/api/system/menu.ts'
import MenuModal from '@/views/system/menu/MenuModal.vue'
import {QuestionCircleOutlined} from '@ant-design/icons-vue'
import {message} from 'ant-design-vue'
import {CreateIcon} from '@/components/CustomIcon.ts'

const searchForm = reactive({
  keyword: '',
  pageNum: 1,
  pageSize: 500,
})
const switchLoading = ref(false)
const dataSource = ref()
const columns: any = [
  {
    title: t('菜单名称'),
    dataIndex: 'name',
    key: 'name',
  },
  {
    title: t('菜单类型'),
    dataIndex: 'type',
    key: 'type',
    width: 100,
  },
  {
    title: t('排序'),
    dataIndex: 'sort',
    key: 'sort',
    width: 60,
  },
  {
    title: t('权限码'),
    dataIndex: 'permission',
    key: 'permission',
    width: 120,
  },
  {
    title: t('按钮策略'),
    dataIndex: 'unauthorizedStrategy',
    key: 'permission',
    width: 120,
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
    key: 'operation',
    fixed: 'right',
  },
]

const queryList = () => {
  getMenuTree().then((res: any) => {
    dataSource.value = res
  })
}
queryList()
const menuModalRef = ref()
const saveOrUpdate = (menu: any = null, isEdit = false) => {
  menuModalRef.value.showModal(menu, isEdit)
}
const deleteMenu = (menuId) => {
  deleteMenuById(menuId).then(() => {
    queryList()
    message.success(t('删除成功'))
  })
}
</script>
