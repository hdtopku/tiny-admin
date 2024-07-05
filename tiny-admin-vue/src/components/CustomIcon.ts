import * as icons from '@ant-design/icons-vue';
import {createVNode} from 'vue'

export const CreateIcon = (props: any) => {
    const {icon} = props;
    let node = icons[icon]
    if (!node) return null
    return createVNode(node);
}
export const GetIcon = (icon: string) => {
    let node = icons[icon]
    if (!node) return null
    return createVNode(node);
}