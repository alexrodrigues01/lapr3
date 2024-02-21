CREATE OR REPLACE PROCEDURE pcrAtribuirEncomenda(p_encomenda Encomenda.id%TYPE, p_carga Encomenda.carga%TYPE) IS
	
	v_carga_maxima_possivel Estafeta.cargaMaxima%TYPE;
	
	v_id_entrega Entrega.id%TYPE;
    
    v_flag int;

BEGIN

	SELECT MAX(cargaMaxima) INTO v_carga_maxima_possivel
	FROM Estafeta;

	-- vai buscar todas as entregas que são possiveis disponiveis para carregar esta encomenda
    
    SELECT COUNT(id) INTO v_flag
    FROM Entrega
    WHERE id_estadoEntrega = 1 
        AND (cargaTotal + p_carga) < v_carga_maxima_possivel;
	
	-- caso não haja alguma entrega vai criar uma nova entrega no estado 1 com aquela encomenda, se houver, então vai dar update à cara total da entrega;
	IF v_flag = 0
	THEN 
		SELECT MAX(id) INTO v_id_entrega
		FROM Entrega;
		
		IF v_id_entrega IS NULL
		THEN 
			v_id_entrega := 1;
		ELSE
			v_id_entrega := v_id_entrega + 1;
		END IF;
		
		INSERT INTO Entrega (id, cargaTotal, id_estadoEntrega) VALUES (v_id_entrega, p_carga, 1);
		
	ELSE 
    
        SELECT id INTO v_id_entrega
        FROM Entrega
        WHERE id_estadoEntrega = 1 
            AND (cargaTotal + p_carga) < v_carga_maxima_possivel
            AND ROWNUM = 1
        ORDER BY cargaTotal;
        
		UPDATE entrega 
		SET cargaTotal = cargaTotal + p_carga
		WHERE id = v_id_entrega;
		
	END IF;
	
	UPDATE Encomenda
	SET id_Entrega = v_id_entrega
	WHERE id = p_encomenda;

END;
