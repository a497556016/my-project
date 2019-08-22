<template>
    <div>
        <m-table v-model="assignedResources" :columns="columns" :page="true" >
            <a-switch slot="assign" slot-scope="{record}" v-model="record.assigned" @change="() => handlerAssigned(record)"></a-switch>
        </m-table>
    </div>
</template>

<script>
    import MTable from "../../../components/table/Table";
    import {createNamespacedHelpers} from 'vuex'
    const permissionStore  = createNamespacedHelpers("permission");

    import types from '../../../store/types'
    export default {
        name: "AssignResource",
        components: {MTable},
        data(){
            return {
                columns: [
                    {title: '名称', dataIndex: 'name'},
                    {title: '编码', dataIndex: 'code'},
                    {title: '操作', slot: 'assign'}
                ],
            }
        },
        computed: {
            ...permissionStore.mapState({
                assignedResources: state => state.assignedResources
            })
        },
        methods: {
            ...permissionStore.mapMutations({
                putEditPermissionResources: types.permissionTypes.PUT_EDIT_PERMISSION_RESOURCES
            }),
            handlerAssigned(record){
                console.log(record)
                this.putEditPermissionResources(record);
            }
        }
    }
</script>

<style scoped>

</style>