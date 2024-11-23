<script lang="ts" setup>
import { saveOrUpdateMenu } from '@/api/system/menu.ts';
import { message } from 'ant-design-vue';
import IconSelector from '@/components/IconSelector.vue';
import { useUserStore } from '@/store';
import { DefaultOptionType } from 'ant-design-vue/es/vc-tree-select/TreeSelect';
import {useWindowSize} from "@vueuse/core";
import { useDebounceFn } from '@vueuse/core';

const {width} = useWindowSize()
const props = defineProps({
  menuTree: Array as () => DefaultOptionType[],
});

const getMenuTree = () => {
  const dfs = (menus: any) => {
    if (!menus?.length) return [];
    let newMenus: any = [];
    menus
        .filter((menu: any) => menu.type === 1)
        .forEach((menu: any) => {
          let newMenu: any = { ...menu };
          if (menu?.children?.length) {
            newMenu.children = dfs(menu.children);
          }
          newMenus.push(newMenu);
        });
    return newMenus;
  };
  return dfs(props.menuTree);
};

const menuTree = computed(() => getMenuTree()),
    open = ref(false),
    isUpdate = ref(false),
    formRef = ref();

const defaultForm = {
  name: '',
  url: '',
  component: '',
  icon: [],
  sort: 9999,
  hidden: false,
  keepAlive: true,
  parentId: null,
  type: 1,
  unauthorizedStrategy: 0,
  permission: '',
};

const form: any = ref({ ...defaultForm }),
    loading = ref(false),
    btnFormRef = ref();
const emit = defineEmits(['queryList']);
const handleOk = async () => {
  const submitForm = async () => {
    loading.value = true;
    try {
      if (!isUpdate.value && form.value.type === 2) {
        form.value.component = null;
      }
      if (!form.value.icon?.length) {
        form.value.icon = null;
      }
      await saveOrUpdateMenu(form.value);
      message.success('Success');
      open.value = false;
      emit('queryList');
      await useUserStore().refreshUserInfo();
    } catch (error) {
      message.error(`Error: ${error.message || 'Unknown error'}`);
    } finally {
      loading.value = false;
    }
  };

  try {
    if (form.value.type === 1) {
      await formRef.value.validate();
    } else {
      await btnFormRef.value.validate();
    }
    await submitForm();
  } catch {
    message.error('Validation failed, please check your input');
  }
};


const userInputPerm = ref(false),
    userInputComponent = ref(false)


const handleUrlChange = useDebounceFn(() => {
  if (isUpdate.value) return;
  if (!userInputPerm.value) {
    form.value.permission = form.value.url
        ?.toLowerCase()
        ?.replace(/\//g, ':');
  }
  if (!userInputComponent.value) {
    form.value.component = form.value.url;
  }
}, 300);


watch(
    () => form.value.url,
    () => {
      while (form.value.url?.startsWith('/')) {
        form.value.url = form.value.url.slice(1);
      }
      if (form.value.url?.endsWith('.vue')) {
        form.value.url = form.value.url?.slice(0, -4);
      }
      if (form.value.url?.length) {
        form.value.url = form.value.url
            ?.replace(/_/g, '-')
            .replace(/ /g, '-')
            .replace(/--/g, '-')
            .replace(/__/g, '-')
            .toLowerCase();
      }
    }
);

watch(
    () => form.value.component,
    () => {
      if (form.value.component?.startsWith('views/')) {
        form.value.component = form.value.component.slice(6);
      }
      if (form.value.component?.endsWith('.vue')) {
        form.value.component = form.value.component.slice(0, -4);
      }
      while (form.value.component?.startsWith('/')) {
        form.value.component = form.value.component.slice(1);
      }
    }
);

let curName;
defineExpose({
  openModal(record: any = {}) {
    open.value = true;
    isUpdate.value = !!record.id;
    const {
      id,
      name,
      url,
      component,
      icon,
      sort,
      hidden,
      parentId,
      type,
      permission,
      unauthorizedStrategy,
      keepAlive,
    } = record;
    form.value = {
      ...defaultForm,
      id,
      name,
      url,
      component,
      icon,
      sort,
      hidden,
      parentId,
      type,
      permission,
      unauthorizedStrategy,
      keepAlive,
    };
    curName = record.name;
  },
});

const rules: any = {
  parentId: [
    {
      required: false,
      message: 'Please select parent menu',
      trigger: ['blur', 'change'],
    },
  ],
  name: [
    {
      required: true,
      message: 'Please enter menu name',
      trigger: ['blur', 'change'],
    },
    {
      min: 2,
      max: 50,
      message: 'Length should be between 2 and 50',
      trigger: ['blur', 'change'],
    },
  ],
  url: [
    {
      required: true,
      message: 'Please enter menu URL',
      trigger: ['blur', 'change'],
    },
    {
      min: 2,
      max: 200,
      message: 'Length should be between 2 and 200',
      trigger: ['blur', 'change'],
    },
  ],
  type: [
    {
      required: true,
      message: 'Please select menu type',
      trigger: ['blur', 'change'],
    },
  ],
};

const buttonRules: any = {
  name: [
    {
      required: true,
      message: 'Please enter button name',
      trigger: ['blur', 'change'],
    },
    {
      min: 2,
      max: 50,
      message: 'Length should be between 2 and 50',
      trigger: ['blur', 'change'],
    },
  ],
};
</script>

<template>
  <a-drawer :size="width<768 ? 'default' : 'large'" v-model:open="open" :root-style="{ color: 'blue' }" placement="right" style="color: red">
    <template #title>
      <span v-if="isUpdate">
        <span>Edit {{ form.type === 2 ? 'Button' : 'Menu' }}：</span>
        <span class="text-gray-400">{{ curName }}</span>
      </span>
      <span>Add {{ form.type === 2 ? 'Button' : 'Menu' }}</span>
    </template>
    <template #footer>
      <a-space class="flex justify-end">
        <a-button key="back" @click="open = false">Cancel</a-button>
        <a-button key="submit" :loading="loading" type="primary" @click="handleOk">Submit</a-button>
      </a-space>
    </template>
    <a-tabs v-model:activeKey="form.type" type="card">
      <template #rightExtra>
        <span class="text-gray-400">
          Selected <a-tag class="text-gray-400">{{ form.type === 2 ? 'Button' : 'Menu' }}</a-tag>
        </span>
      </template>
      <a-tab-pane :key="1" tab="Menu">
        <a-form ref="formRef" :label-col="{ span: 5 }" :model="form" :rules="rules" class="pt-4">
          <a-form-item help="If not selected, it will be the top-level menu." label="Parent Menu" name="parentId">
            <a-tree-select
                v-model:value="form.parentId"
                :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
                :field-names="{ children: 'children', label: 'name', value: 'id' }"
                placeholder="Please select parent menu"
                :tree-data="menuTree"
                allow-clear
                show-search
                style="width: 100%"
                tree-default-expand-all
                tree-node-filter-prop="name"
            >
              <template #title="{ id: val, label }">
                <a-tag v-if="val === form.parentId" color="blue">{{ label }}</a-tag>
                <span v-else>{{ label }}</span>
              </template>
            </a-tree-select>
          </a-form-item>
          <a-form-item label="Name" name="name">
            <a-input v-model:value="form.name" placeholder="Please enter menu name" allow-clear autocomplete="off" />
          </a-form-item>
          <a-form-item label="Access URL" name="url">
            <div class="flex items-center">
              <span>/</span>
              <a-input
                  v-model:value="form.url"
                  placeholder="Please enter access URL"
                  allow-clear
                  autocomplete="off"
                  @change="handleUrlChange"
              />
            </div>
          </a-form-item>
          <a-form-item label="Component location" name="component">
            <div class="flex items-center">
              <span>views/</span>
              <a-input
                  v-model:value="form.component"
                  placeholder="Please enter component location"
                  allow-clear
                  autocomplete="off"
                  @keyup="() => (userInputComponent = true)"
              >
                <template #suffix> .vue</template>
              </a-input>
            </div>
          </a-form-item>
          <a-form-item
              label="Permission"
              tooltip="If not filled, it will be generated automatically based on the access URL"
              name="permission"
          >
            <a-input
                v-model:value="form.permission"
                placeholder="Please enter permission"
                allow-clear
                autocomplete="off"
                @keyup="() => (userInputPerm = true)"
            />
          </a-form-item>
          <div class="grid grid-cols-2">
            <a-form-item
                help="The smaller the value, the higher the priority."
                label="Sort"
                :label-col="{ span: 10 }"
            >
              <a-input-number
                  v-model:value="form.sort"
                  :defaultValue="9999"
                  :max="9999"
                  :min="1"
                  allow-clear
                  autocomplete="off"
              />
            </a-form-item>
            <a-form-item
                label="Logo"
                :label-col="{ span: 10 }"
                name="icon"
            >
              <icon-selector v-model:value="form.icon"/>
            </a-form-item>
          </div>
          <a-form-item
              label="Redirect to for unauthorized roles."
              :label-col="{ span: 12 }"
              tooltip="Note: If redirected to a 404 page, the menu, sub-menu, and sidebar will be hidden from unauthorized users."
          >
            <a-radio-group
                v-model:value="form.unauthorizedStrategy"
                name="radioGroup"
            >
              <a-radio :value="0">404 Page</a-radio>
              <a-radio :value="1">403 Forbidden</a-radio>
            </a-radio-group>
          </a-form-item>
          <div class="grid grid-cols-2">
            <a-form-item label="Hide Menu" :label-col="{ span: 10 }">
              <a-switch
                  v-model:checked="form.hidden"
                  checked-children="Shown"
                  un-checked-children="Hidden"
              />
            </a-form-item>
            <a-form-item
                label="Cache Page"
                :label-col="{ span: 12 }"
                tooltip="Caching the page will prevent it from reloading when switching, improving the efficiency of page transitions."
            >
              <a-switch
                  v-model:checked="form.keepAlive"
                  checked-children="Cached"
                  un-checked-children="Not Cached"
              />
            </a-form-item>
          </div>
        </a-form>
      </a-tab-pane>
      <a-tab-pane :key="2" tab="Button">
        <a-form
            ref="btnFormRef"
            :label-col="{ span: 6 }"
            :model="form"
            :rules="buttonRules"
            class="pt-4"
        >
          <a-form-item label="Parent Menu" name="parentId">
            <a-tree-select
                v-model:value="form.parentId"
                :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
                :field-names="{
          children: 'children',
          label: 'name',
          value: 'id'
        }"
                placeholder="Please select parent menu"
                :tree-data="menuTree"
                allow-clear
                show-search
                style="width: 100%"
                tree-default-expand-all
                tree-node-filter-prop="name"
            >
              <template #title="{ id: val, label }">
                <a-tag v-if="val === form.parentId" color="blue">{{ label }}</a-tag>
                <span v-else>{{ label }}</span>
              </template>
            </a-tree-select>
          </a-form-item>
          <a-form-item label="Button Name" name="name">
            <a-input
                v-model:value="form.name"
                placeholder="Please enter button name"
                allow-clear
                autocomplete="off"
            />
          </a-form-item>
          <a-form-item
              label="API URL"
              tooltip="The URL of the backend API, e.g., /system/sysmenu/saveorupdate. Unauthorized users are not allowed to access the API."
              name="url"
          >
            <div class="flex items-center">
              <span>/</span>
              <a-input
                  v-model:value="form.url"
                  placeholder="Please enter backend API URL"
                  allow-clear
                  autocomplete="off"
                  @change="handleUrlChange"
              />
            </div>
          </a-form-item>
          <a-form-item
              help="When hiding the button or displaying it as disabled, this field must be filled out for it to take effect."
              label="Permission Identifier"
              tooltip="Permission identifier (e.g., user:add, user:edit, role:add). Used with Auth.vue component to control button visibility, clickability, etc., for unauthorized users."
              name="permission"
          >
            <a-input
                v-model:value="form.permission"
                placeholder="Please enter button permission identifier"
                allow-clear
                autocomplete="off"
            />
          </a-form-item>
          <a-form-item label="Unauthorized Strategy" name="sort">
            <a-radio-group v-model:value="form.unauthorizedStrategy" name="radioGroup">
              <a-radio value="0">Hide Button</a-radio>
              <a-radio value="1">Show but Disabled</a-radio>
              <a-radio value="2">Clickable but Submission Fails</a-radio>
            </a-radio-group>
          </a-form-item>
        </a-form>
      </a-tab-pane>

    </a-tabs>
  </a-drawer>
</template>
