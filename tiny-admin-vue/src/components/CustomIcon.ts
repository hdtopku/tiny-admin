import * as icons from '@ant-design/icons-vue';
import {createVNode} from 'vue'

export const CreateIcon = (props: any) => {
    const {icon} = props;
    return createVNode(icons[icon]);
}
export const GetIcon = (icon: string) => {
    return createVNode(icons[icon]);
}