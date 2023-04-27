// 获取表单元素
const orderForm = document.getElementById("order-form");
const orderIdInput = document.getElementById("order-id");
const trackResult = document.getElementById("track-result");

// 提交表单时触发的函数
function handleSubmit(event) {
    event.preventDefault(); // 阻止表单默认提交事件
    const orderId = orderIdInput.value; // 获取订单号
    // 模拟向服务器发送请求，获取订单状态
    const status = getOrderStatus(orderId);
    // 显示订单状态
    trackResult.textContent = `订单 ${orderId} 的状态为 ${status}`;
}

// 模拟向服务器发送请求，获取订单状态的函数
function getOrderStatus(orderId) {
    // 在此处编写向服务器发送请求的代码
    // 此处为了方便直接返回假的订单状态
    const statusList = ["已下单", "待发货", "运输中", "已签收"];
    const index = Math.floor(Math.random() * statusList.length);
    return statusList[index];
}

// 绑定表单提交事件
orderForm.addEventListener("submit", handleSubmit);
