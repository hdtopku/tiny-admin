<script lang="ts" setup>

import {deleteMenuById, getMenuTree} from "@/api/menu.ts";
import MenuModal from "@/views/system/menu/MenuModal.vue";
import {QuestionCircleOutlined} from "@ant-design/icons-vue";
import {message} from "ant-design-vue";
import {CreateIcon} from "@/components/CustomIcon.ts";

const searchForm = reactive({
  keyword: '',
  pageNum: 1,
  pageSize: 500,
})
const switchLoading = ref(false)
const dataSource = ref()
const columns: any = [
  {
    title: '菜单名称',
    dataIndex: 'name',
    key: 'name',
  },
  {
    title: '菜单类型',
    dataIndex: 'type',
    key: 'type',
    width: 100,
  },
  {
    title: '排序',
    dataIndex: 'sort',
    key: 'sort',
    width: 60,
  },
  {
    title: '权限码',
    dataIndex: 'permission',
    key: 'permission',
    width: 120,
  },
  {
    title: '按钮策略',
    dataIndex: 'unauthorizedStrategy',
    key: 'permission',
    width: 120,
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    key: 'createTime',
  },
  {
    title: '更新时间',
    dataIndex: 'updateTime',
    key: 'updateTime',
  },
  {
    title: '操作',
    key: 'operation',
    fixed: 'right',
  }
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
    message.success('删除成功')
  })
}
</script>

<template>
  <div>
  <div class="p-4">
    <div class="flex mb-4">
      <div class="flex items-center gap-4 mx-auto sm:w-[80%] w-full">
        <Auth :has-permission="'menu:add'">
          <a-button type="primary" @click="()=>saveOrUpdate()">新增</a-button>
        </Auth>
        <a-input @keydown.enter.prevent="queryList" allow-clear class="text-left"
                 placeholder="搜索菜单名称" type="text"
                 id="keyword"
                 v-model:value="searchForm.keyword" autocomplete="off">
          <template #prefix>
            <a-switch :loading="switchLoading" class="flex-shrink-0"
                      checked-children="已启用" un-checked-children="已禁用"/>
          </template>
          <template #suffix>
            <a-button type="primary" @click="queryList">搜索</a-button>
          </template>
        </a-input>
      </div>
    </div>
    <a-table :columns="columns" :data-source="dataSource" :loading="switchLoading"
             :scroll="{ x: 'max-content', y: 'calc(100vh - 200px)' }" row-key="id">
      <template #bodyCell="{record, column}">
        <template v-if="column.dataIndex === 'name'">
          <CreateIcon :icon="record.icon"/>
          {{ record.name }}
        </template>
        <template v-if="column.dataIndex === 'type'">
          <template v-if="record.type === 0">
            目录
          </template>
          <template v-if="record.type === 1">
            菜单
          </template>
          <template v-if="record.type === 2">
            按钮
          </template>
        </template>
        <template v-if="column.key === 'operation'">
          <a-button type="link" @click="saveOrUpdate(record, true)">修改</a-button>
          <Auth :has-permission="'system:sysmenu:deletebyids'">
          <a-popconfirm cancel-text="否" ok-text="是" ok-type="danger" title="是否删除该菜单？"
                        @confirm="()=>deleteMenu(record.id)">
            <template #icon>
              <question-circle-outlined style="color: red"/>
            </template>
            <a-button type="link" danger>删除</a-button>
          </a-popconfirm>
          </Auth>
          <a-button v-show="record.type === 1" link type="link" @click="()=>saveOrUpdate({parentId: record.id},false )">新增</a-button>
        </template>
      </template>
    </a-table>
  </div>
  <MenuModal @query-list="queryList" ref="menuModalRef" :menuTree="dataSource"/>
  </div>
</template>

<style scoped>

</style>