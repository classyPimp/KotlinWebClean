class CreateUserDefinableRelationReasons < ActiveRecord::Migration[5.1]
  def change
    create_table :user_definable_relation_reasons do |t|
      t.string :reason_name
      t.string :for_type
      t.string :sub_identifier
      t.string :reason_description
      t.timestamps
    end
  end
end
