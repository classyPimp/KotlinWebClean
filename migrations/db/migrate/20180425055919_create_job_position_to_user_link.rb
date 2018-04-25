class CreateJobPositionToUserLink < ActiveRecord::Migration[5.1]
  def change
    create_table :job_position_to_user_links do |t|
      t.references :user, foreign_key: true
      t.boolean :is_delegated
      t.references :job_position_delegation, foreign_key: true
      t.timestamps
    end
  end
end
