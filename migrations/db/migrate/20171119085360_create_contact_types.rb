class CreateContactTypes < ActiveRecord::Migration[5.1]
  def change
    create_table :contact_types do |t|
      t.string :name
      t.string :is_specific_for_type
      t.timestamps
    end
  end
end
