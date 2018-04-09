class CreateApprovalRejections < ActiveRecord::Migration[5.1]
  def change
    create_table :approval_rejections do |t|
      t.references :approval, foreign_key: true
      t.references :user, foreign_key: true
      t.string :reason_text
      t.timestamps
    end
  end
end
