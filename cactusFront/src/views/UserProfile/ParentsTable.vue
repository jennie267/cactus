<template>
    <div class="card shadow" style="width:100%;">
        <div class="card-header box3Header">
            <div class="row">
                <h3 class="mb-0 todaySchHeaderStr"><h1 class="ni ni-badge todaySchHeaderStr"></h1>  부모님목록</h3>
            </div>
        </div>
        <div class="card-body">
            <div class="row">
                <vue-good-table
                        style="width:100%; height:100%;"
                        :columns="parentHeader"
                        :rows="parentData"
                        @on-row-click="onRowClick"/>
            </div>
        </div>
        <base-button style="background-color: #3a6fa0; border-color: #3a6fa0" @click="addParentModal()">부모등록</base-button>

        <family-sel-modal ref="addParent"></family-sel-modal>
    </div>
</template>

<script>
import 'vue-good-table/dist/vue-good-table.css'
import { VueGoodTable } from 'vue-good-table/src'

import FamilySelModal from './FamilySelModal'
    export default {
        components: {
            VueGoodTable,
            FamilySelModal
        },
        watch: {
            getDeletedParentId (val) {
                this.deleteParent(val);
            },
        },
        computed: {
            getDeletedParentId () {
                return this.$store.getters.getDeletedParentId;
            },
        },
        data() {
            return {
                user: this.$store.state.user,
                parentHeader: [
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
                parentData: [],
            }
        },
        methods: {
            onRowClick(select) {
                this.$emit('selectUser', select.row)
            },
            getParents(){
                this.$http.get(`/user/parents/${this.user.userId}`,  { headers: { Authorization: `Bearer ${this.user.token}` } })
                    .then(res => {
                        this.parentData = res.data.users;
                        this.parentData.forEach(user => user.profile = '<a class="avatar avatar-sm rounded-circle" style="cursor: pointer;">\n' +
                            '<img alt="" src="'+user.profileUrl+'" style="width:90%;"/>\n' +
                            '</a>');
                    });
            },
            addParentModal(){
                this.$refs.addParent.openModal();
            },
            delParents(users){
                this.parentData = users;

            },addParent(users){
                users.profile = '<a class="avatar avatar-sm rounded-circle" style="cursor: pointer;">\n' +
                    '<img alt="" src="'+users.profileUrl+'" style="width:90%;"/>\n' +
                    '</a>';
                this.parentData.push(users);
            },
            deleteParent(userId){
                this.parentData = this.parentData.filter(function(parent){
                    return parent.userId !== userId;
                });
            },
        },
        mounted() {
            this.getParents();
        }
    }
</script>

<style scoped>

</style>
