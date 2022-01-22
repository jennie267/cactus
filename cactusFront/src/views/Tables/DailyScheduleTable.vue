<template>
    <div class="card" >
        <div class="card-header border-0" >
            <div class="row align-items-center">
                <div class="col">
                    <h3 id="clickSchedule" class="mb-0">
                        {{date}} 일정
                    </h3>
                </div>
                <write-schedule-modal :date="date" :selectedParent="selectedParent"></write-schedule-modal>
                <update-schedule-modal ref="updateModal"></update-schedule-modal>
            </div>
        </div>
        <div class="table-responsive">
            <base-table class="table align-items-center table-flush"
                        :thead-classes="type='thead-light'"
                        tbody-classes="list"
                        :data="tableData">
                <template slot="columns">
                    <th>제목</th>
                    <th>내용</th>
                    <th>시간</th>
                    <th></th>
                    <th></th>
                </template>
                <template slot-scope="{row}" style="width: 100%;">
                    <td class="media align-items-center" style="font-size: 15px; cursor:pointer; width:15%;" @click.prevent="showUpdModal(row)">
                        {{row.periodName}}
                    </td>
                    <td class="budget" style="font-size: 15px; cursor:pointer; width:15%;" @click.prevent="showUpdModal(row)">
                        <div class="name overText"> {{row.periodRemark}}</div>
                    </td>
                    <td style="font-size: 15px; cursor:pointer; width:15%;" @click.prevent="showUpdModal(row)">
                        {{row.formattedTime}}
                    </td>
                    <td class="text-right" style="width:10%">
                        <base-button class="btn-primary btn-sm" @click="delSchedule(row.schdId)">삭제</base-button>
                    </td>
                </template>
            </base-table>
        </div>
        <div class="card-footer d-flex justify-content-end" >
            <!-- <base-pagination total="30"></base-pagination> -->
        </div>
    </div>
</template>
<script>
    import WriteScheduleModal from '../Modals/WriteScheduleModal'
    import UpdateScheduleModal from '../Modals/UpdateScheduleModal'
    var moment = require('moment');
    moment().format();

    export default {
        name: 'daily-schedule-table',
        props: {
            selectedParent: Object,
        },
        components:{
            WriteScheduleModal,
            UpdateScheduleModal
        },
        methods: {
            showUpdModal: function(period){
                this.$refs.updateModal.period = period;
                this.$refs.updateModal.modal = true;
            },
            delSchedule: function(schdId) {
                if(schdId===0) return;
                this.$http.post(`/period/schedule/delete/`+schdId, null,  { headers: { Authorization: `Bearer ${this.user.token}` } })
                    .then(res => {
                        if(res.status===200){
                            this.$swal({
                                type: 'success',
                                title: '삭제되었습니다.'
                            });
                            this.$store.commit('setDeletedSchdId', schdId);
                        }
                    })
            },
        },
        data() {
            return {
                user: this.$store.state.user,
                date: '',
                tableData: [],
                selectedId: 0,
            }
        },
    }
</script>
<style scoped>
    .overText {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        font-size: 15px;
        max-width: 220px;
    }
</style>
