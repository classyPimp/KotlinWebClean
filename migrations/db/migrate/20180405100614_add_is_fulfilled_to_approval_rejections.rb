class AddIsFulfilledToApprovalRejections < ActiveRecord::Migration[5.1]
  def change
    add_column :approval_rejections, :fulfilled, :timestamp
  end
end
