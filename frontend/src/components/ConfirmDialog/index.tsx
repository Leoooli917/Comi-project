import { Modal, Button } from 'antd';
import './index.less'

interface ConfirmDialogProps {
  visible: boolean;
  alertType: 'delete' | 'offline';
  title: string;
  content: string;
  cardTitle?: string;
  onConfirm: () => void;
  onCancel: () => void;
}

const ConfirmDialog: React.FC<ConfirmDialogProps> = ({ visible, alertType, title, content, cardTitle, onConfirm, onCancel }) => {
  const getContent = () => {
    switch (alertType) {
      case 'delete':
        return `是否删除《${cardTitle}》?`;
      case 'offline':
        return `是否把《${cardTitle}》进行下线处理?`;
      default:
        return content;
    }
  };

  return (
    <Modal
      title={title}
      visible={visible}
      centered={true}
      onCancel={onCancel}
      footer={[
        <Button key="cancel" onClick={onCancel}>
          取消
        </Button>,
        <Button key="confirm" type="primary" onClick={onConfirm}>
          确定
        </Button>,
      ]}
      className="custom-modal"
    > 
      <p>{getContent()}</p>
      <hr className="modal-divider" />
    </Modal>
  );
};

export default ConfirmDialog;

