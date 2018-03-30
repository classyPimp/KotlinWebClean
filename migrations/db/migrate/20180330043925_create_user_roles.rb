class CreateUserRoles < ActiveRecord::Migration[5.1]
  def change
    create_table :user_roles do |t|
      t.string :name
      t.boolean :is_specific
      t.string :specific_to_type
      t.bigint :specific_to_id
      t.references :user
      t.timestamps
    end
  end
end
