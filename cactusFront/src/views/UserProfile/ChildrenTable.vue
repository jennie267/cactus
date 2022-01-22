<template>
    <div class="card shadow" style="width:100%;">
        <div class="card-header box3Header2">
            <div class="row">
                <h3 class="mb-0 todaySchHeaderStr"><h1 class="ni ni-badge todaySchHeaderStr"></h1>  자녀목록</h3>
            </div>
        </div>
        <div class="card-body">
            <div class="row">
                <vue-good-table
                        style="width:100%; height:100%;"
                        :columns="childHeader"
                        :rows="childData"
                        @on-row-click="onRowClick"/>
            </div>
        </div>
        <base-button style="background-color: #3a6fa0; border-color: #3a6fa0" @click="addChildrenModal()">자녀등록</base-button>

        <child-sel-modal ref="addChild"></child-sel-modal>
    </div>
</template>

<script>
import 'vue-good-table/dist/vue-good-table.css'
import { VueGoodTable } from 'vue-good-table/src'

import ChildSelModal from './ChildSelModal'

    export default {
        components: {
            VueGoodTable,
            ChildSelModal
        },
        watch: {
            getDeletedChildId (val) {
                this.deleteChild(val);
            },
        },
        computed: {
            getDeletedChildId () {
                return this.$store.getters.getDeletedChildId;
            },
        },
        data() {
            return {
                user: this.$store.state.user,
                childHeader: [
                    {
                        field: 'userId',
                        hidden: true,
                    },
                    {
                        label: '사진',
                        field: 'profile',
                        width: '8%',
                        tdClass: 'text-center',
                        html: true,
                    },
                    {
                        label: '이름',
                        field: 'name',
                        width: '25%',
                    },
                    {
                        label: '아이디',
                        field: 'id',
                        width: '25%',
                    },
                    {
                        label: '생일',
                        field: 'birthday',
                        width: '25%',
                    },
                ],
                childData: [],
            }
        },
        methods: {
            onRowClick(select) {
                this.$emit('selectUser', select.row)
            },
            addChildrenModal(){
                this.$refs.addChild.openModal();
            },
            getChildren(){
                this.$http.get(`/user/children/${this.user.userId}`,  { headers: { Authorization: `Bearer ${this.user.token}` } })
                    .then(res => {
                        this.childData = res.data.users;
                        this.childData.forEach(user => user.profile = '<a class="avatar avatar-sm rounded-circle" style="cursor: pointer;">\n' +
                            '<img alt="" src="'+user.profileUrl+'" style="width:90%;"/>\n' +
                            '</a>');
                    });
            },
            delChildren(users){
                this.childData = users;
            }, addChildren(users){
                users.profile = '<a class="avatar avatar-sm rounded-circle" style="cursor: pointer;">\n' +
                    '<img alt="" src="'+users.profileUrl+'" style="width:90%;"/>\n' +
                    '</a>';
                this.childData.push(users);
            },
            deleteChild(userId){
                this.childData = this.childData.filter(function(child){
                    return child.userId !== userId;
                });
            },
        },
        mounted() {
            this.getChildren();
        }
    }
</script>

<style scoped>

    /*#f5c836*/

</style>
