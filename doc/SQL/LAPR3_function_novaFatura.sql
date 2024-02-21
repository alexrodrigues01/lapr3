CREATE OR REPLACE FUNCTION fncNovaFatura(p_encomenda Encomenda.id%TYPE, p_numeroCreditosDescontados Fatura.desconto%TYPE, p_taxa_entrega Fatura.taxaEntrega%TYPE, p_nif Fatura.nif%TYPE, p_farmacia Farmacia.id%TYPE) RETURN Farmacia.id%TYPE IS

	v_id_fatura Fatura.id%TYPE;
	v_nome Produto.nome%TYPE;
	v_linha LinhaFatura.linha%TYPE;
	v_quantidade LinhaFatura.quantidade%TYPE;
	v_preco_unitario LinhaFatura.preco_unitario%TYPE;
	v_iva LinhaFatura.iva%TYPE;
	v_produto Produto.id%TYPE;
	v_valor_total Fatura.valorProdutos%TYPE;
    v_valor_linha LinhaFatura.valor%TYPE;

    Cursor c_linhaEncomendaProduto IS
        SELECT Produto.id, Produto.nome, Produto.preco_unitario, LinhaEncomendaProduto.quantidade, Produto.iva
        FROM LinhaEncomendaProduto
            INNER JOIN Produto ON Produto.id = LinhaEncomendaProduto.id_produto
        WHERE LinhaEncomendaProduto.id_encomenda = p_encomenda;

BEGIN


-- vai bscar o id da ultima fatura criada
    SELECT MAX(Fatura.id) INTO v_id_fatura
    FROM Fatura;

-- caso nao haja nenhuma fatura
    IF v_id_fatura IS NULL
    THEN
        v_id_fatura := 1;
	ELSE
		v_id_fatura := v_id_fatura +1;
    END IF;

-- cria a fatura (ainda sem valores de reserva e de consumo)
    INSERT INTO Fatura(id, data, desconto, taxaEntrega, nif, id_farmacia) VALUES (v_id_fatura, CURRENT_DATE, p_numeroCreditosDescontados, p_taxa_entrega, p_nif, p_farmacia);

    v_linha := 0;
	v_valor_total := 0;

-- vai buscar todas as informacoes necessarias a LINHA_CONTA_CONSUMO associadas a RESERVA passada por parametro

    OPEN c_linhaEncomendaProduto;

    LOOP
        FETCH c_linhaEncomendaProduto INTO v_produto, v_nome, v_preco_unitario, v_quantidade, v_iva;

        EXIT WHEN c_linhaEncomendaProduto%NOTFOUND;

        v_valor_linha := (v_preco_unitario + ((v_preco_unitario * v_iva)/100)) * v_quantidade;
				v_valor_total := v_valor_total + v_valor_linha;
        v_linha := v_linha +1;

    -- cria a linha de fatura refente à fatura daquela encomenda
        INSERT INTO LinhaFatura(id_fatura, linha, nomeProduto, valor, quantidade, preco_unitario, iva, id_produto, id_encomenda)  VALUES (v_id_fatura, v_linha, v_nome, v_valor_linha, v_quantidade, v_preco_unitario, v_iva, v_produto, p_encomenda);

    END LOOP;

	v_valor_total := v_valor_total + p_taxa_entrega - p_numeroCreditosDescontados;

-- atribui o valor total da encomenda a fatura
	UPDATE Fatura
	SET valorProdutos = v_valor_total
	WHERE id = v_id_fatura;

	return v_id_fatura;

    CLOSE c_linhaEncomendaProduto;

END;
