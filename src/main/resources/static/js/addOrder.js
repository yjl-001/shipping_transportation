// 获取表单元素
const form = document.querySelector('#order-form');
const goodsDescriptionInput = form.querySelector('#goods-description');
const beginAddrInput = form.querySelector('#begin-addr');
const destAddrInput = form.querySelector('#dest-addr');
const priceInput = form.querySelector('#price');
const consignerIdInput = form.querySelector('#consigner-id');
const destTimeInput = form.querySelector('#dest-time');
const companyIdInput = form.querySelector('#company-id');
const driverIdInput = form.querySelector('#driver-id');

// 表单提交事件处理函数
function handleSubmit(event) {
    event.preventDefault();
    // 以下是表单数据的获取方法，具体根据实际情况选择使用
    const formData = new FormData(form);
    const goodsDescription = goodsDescriptionInput.value.trim();
    const beginAddr = beginAddrInput.value.trim();
    const destAddr = destAddrInput.value.trim();
    const price = parseFloat(priceInput.value.trim());
    const consignerId = parseInt(consignerIdInput.value.trim());
    const destTime = new Date(destTimeInput.value.trim());
    const companyId = parseInt(companyIdInput.value.trim());
    const driverId = parseInt(driverIdInput.value.trim());

    // 对表单数据进行验证
    if (goodsDescription === '') {
        alert('货物描述不能为空');
        return;
    }

    if (beginAddr === '') {
        alert('出发地不能为空');
        return;
    }

    if (destAddr === '') {
        alert('目的地不能为空');
        return;
    }

    if (isNaN(price) || price <= 0) {
        alert('价格必须是一个大于零的数字');
        return;
    }

    if (isNaN(consignerId) || consignerId <= 0) {
        alert('发货人 ID 必须是一个大于零的数字');
        return;
    }

    if (isNaN(companyId) || companyId <= 0) {
        alert('承运商 ID 必须是一个大于零的数字');
        return;
    }

    if (isNaN(driverId) || driverId <= 0) {
        alert('司机 ID 必须是一个大于零的数字');
        return;
    }

    // TODO: 将数据提交到后端进行处理
    console.log({
        goodsDescription,
        beginAddr,
        destAddr,
        price,
        consignerId,
        destTime,
        companyId,
        driverId,
    });

    // 提交成功后进行跳转或其他操作
    alert('提交成功！');
    form.reset();
}

// 绑定表单提交事件
form.addEventListener('submit', handleSubmit);
