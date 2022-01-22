function make2letterFormat(num)
{
    return num.length===1?'0'+num:num;
}

export default class dateUtil {
    static getToday()
    {
        let today = new Date();
        return today.getFullYear().toString()+'-'+make2letterFormat((today.getMonth()+1).toString())+'-'+make2letterFormat(today.getDate().toString());
    }
}